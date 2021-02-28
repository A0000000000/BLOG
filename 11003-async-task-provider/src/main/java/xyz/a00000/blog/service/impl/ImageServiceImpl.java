package xyz.a00000.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.ImageBean;
import xyz.a00000.blog.bean.orm.CreatorInfo;
import xyz.a00000.blog.bean.orm.EssayInfo;
import xyz.a00000.blog.bean.orm.Image;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.component.DateTools;
import xyz.a00000.blog.mapper.CreatorInfoMapper;
import xyz.a00000.blog.mapper.EssayInfoMapper;
import xyz.a00000.blog.mapper.ImageMapper;
import xyz.a00000.blog.service.ImageService;
import xyz.a00000.blog.service.mq.RabbitMQProvider;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class ImageServiceImpl extends BaseServiceImpl<Image, ImageMapper> implements ImageService {

    @Autowired
    private RabbitMQProvider rabbitMQProvider;
    @Autowired
    private RedisTemplate<String, ImageBean> redisTemplate;

    @Autowired
    private EssayInfoMapper essayInfoMapper;
    @Autowired
    private CreatorInfoMapper creatorInfoMapper;

    @Autowired
    private DateTools dateTools;

    @Autowired
    private MinioClient minioClient;

    @Override
    @HystrixCommand(fallbackMethod = "uploadImage_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<List<Image>> uploadImage(MultipartFile[] images, Integer essayId, String password, UserDetailsBean currentUserDetails) {
        try {
            log.info("准备上传文件.");
            log.info("校验参数是否合法.");
            if (essayId == null) {
                return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
            }
            log.info("校验是否有权限.");
            QueryWrapper<EssayInfo> qwEssayInfo = new QueryWrapper<>();
            qwEssayInfo.eq("essay_id", essayId);
            EssayInfo info = essayInfoMapper.selectOne(qwEssayInfo);
            if (!info.getCreatorId().equals(currentUserDetails.getCreator().getId())) {
                return BaseServiceResult.getFailedBean(new Exception("ACCESS_DENIED"), 7);
            }
            List<Image> data = new ArrayList<>();
            for (MultipartFile image : images) {
                log.info("生成文件名.");
                String filename = String.format("[%s]-[%d-%d]-[%s]", dateTools.getCurrentDate(), currentUserDetails.getCreator().getId(), essayId, UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5));
                log.info("构建文件数据.");
                Image imageData = new Image();
                imageData.setCreatorId(currentUserDetails.getCreator().getId());
                imageData.setFilename(filename);
                imageData.setUploadTime(new Date());
                imageData.setEssayId(essayId);
                imageData.setOriginalFilename(image.getOriginalFilename());
                imageData.setPassword(password);
                imageData.setContentType(image.getContentType());
                log.info("构建文件保存数据.");
                ImageBean bean = new ImageBean(currentUserDetails.getCreatorInfo().getFileBucket(), filename, ImageBean.TYPE_SAVE, image.getSize(), image.getBytes());
                log.info("生成redis的key.");
                String key = UUID.randomUUID().toString().replaceAll("-", "");
                while (redisTemplate.hasKey(key)) {
                    key = UUID.randomUUID().toString().replaceAll("-", "");
                }
                log.info("保存文件数据到redis");
                ValueOperations<String, ImageBean> ops = redisTemplate.opsForValue();
                ops.set(key, bean);
                log.info("将消息发送至消息队列.");
                rabbitMQProvider.sendImage(key);
                log.info("保存文件数据到数据库.");
                u.insert(imageData);
                data.add(imageData);
            }
            log.info("保存完成, 准备返回数据.");
            return BaseServiceResult.getSuccessBean(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BaseServiceResult<List<Image>> uploadImage_fallback(MultipartFile[] images, Integer essayId, String password, UserDetailsBean currentUserDetails) {
        log.info("uploadImage方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

    @Override
    @HystrixCommand(fallbackMethod = "deleteImageById_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<Void> deleteImageById(Integer id, UserDetailsBean currentUserDetails) {
        log.info("准备删除图片.");
        log.info("加载被删除图片的数据.");
        Image image = u.selectById(id);
        if (image == null || !image.getCreatorId().equals(currentUserDetails.getCreator().getId())) {
            log.info("没有权限删除.");
            return BaseServiceResult.getFailedBean(new Exception("ACCESS_DENIED"), 7);
        }
        log.info("准备构建删除的异步任务.");
        ImageBean imageBean = new ImageBean(currentUserDetails.getCreatorInfo().getFileBucket(), image.getFilename(), ImageBean.TYPE_DELETE, null, null);
        log.info("生成合法的key.");
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        if (redisTemplate.hasKey(key)) {
            key = UUID.randomUUID().toString().replaceAll("-", "");
        }
        log.info("将数据保存至redis.");
        ValueOperations<String, ImageBean> ops = redisTemplate.opsForValue();
        ops.set(key, imageBean);
        log.info("将任务发送至消息队列.");
        rabbitMQProvider.sendImage(key);
        log.info("删除数据库中的记录.");
        u.deleteById(id);
        log.info("返回结果.");
        return BaseServiceResult.getSuccessBean(null);
    }

    public BaseServiceResult<Void> deleteImageById_fallback(Integer id, UserDetailsBean currentUserDetails) {
        log.info("deleteImageById方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

    @Override
    @HystrixCommand(fallbackMethod = "deleteImageByEssayId_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<Void> deleteImageByEssayId(Integer essayId, UserDetailsBean currentUserDetails) {
        log.info("准备删除图片.");
        log.info("校验参数是否合法.");
        if (essayId == null) {
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        log.info("判断是否有权限删除.");
        QueryWrapper<EssayInfo> qwEssayInfo = new QueryWrapper<>();
        qwEssayInfo.eq("creator_id", currentUserDetails.getCreator().getId());
        qwEssayInfo.eq("essay_id", essayId);
        EssayInfo essayInfo = essayInfoMapper.selectOne(qwEssayInfo);
        if (essayInfo == null) {
            return BaseServiceResult.getFailedBean(new Exception("ACCESS_DENIED"), 7);
        }
        log.info("查询待删除的图片信息.");
        QueryWrapper<Image> qwImage = new QueryWrapper<>();
        qwImage.eq("creator_id", currentUserDetails.getCreator().getId());
        qwImage.eq("essay_id", essayId);
        List<Image> images = u.selectList(qwImage);
        log.info("准备删除图片.");
        if (images == null || images.size() == 0) {
            return BaseServiceResult.getSuccessBean(null);
        }
        images.forEach(image -> {
            log.info("构建删除信息的bean.");
            ImageBean bean = new ImageBean(currentUserDetails.getCreatorInfo().getFileBucket(), image.getFilename(), ImageBean.TYPE_DELETE, null, null);
            log.info("生成合法的key.");
            String key = UUID.randomUUID().toString().replaceAll("-", "");
            while (redisTemplate.hasKey(key)) {
                key = UUID.randomUUID().toString().replaceAll("-", "");
            }
            ValueOperations<String, ImageBean> ops = redisTemplate.opsForValue();
            log.info("将数据保存至redis.");
            ops.set(key, bean);
            log.info("将任务发送至消息队列.");
            rabbitMQProvider.sendImage(key);
            log.info("从数据库删除记录.");
            u.deleteById(image.getId());
        });
        return BaseServiceResult.getSuccessBean(null);
    }

    public BaseServiceResult<Void> deleteImageByEssayId_fallback(Integer essayId, UserDetailsBean currentUserDetails) {
        log.info("deleteImageByEssayId方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }


    @Override
    @HystrixCommand(fallbackMethod = "getImageById_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<ImageBean> getImageById(Integer id, String password) {
        try {
            log.info("获得待访问的图片信息.");
            Image image = u.selectById(id);
            log.info("判断是否有权限访问.");
            if (image == null || !StringUtils.isEmpty(image.getPassword()) && !image.getPassword().equals(password)) {
                return BaseServiceResult.getFailedBean(new Exception("ACCESS_DENIED"), 7);
            }
            log.info("获得上传文件的用户, 以得到文件所在的文件桶.");
            QueryWrapper<CreatorInfo> qwCreatorInfo = new QueryWrapper<>();
            qwCreatorInfo.eq("creator_id", image.getCreatorId());
            CreatorInfo creatorInfo = creatorInfoMapper.selectOne(qwCreatorInfo);
            log.info("获得文件数据.");
            InputStream object = minioClient.getObject(creatorInfo.getFileBucket(), image.getFilename());
            byte[] data = object.readAllBytes();
            object.close();
            log.info("封装数据到ImageBean.");
            ImageBean bean = new ImageBean();
            bean.setFilename(image.getOriginalFilename());
            bean.setData(data);
            bean.setType(image.getContentType());
            bean.setFileBucket(creatorInfo.getFileBucket());
            bean.setSize(Long.getLong(String.valueOf(data.length)));
            log.info("返回文件数据.");
            return BaseServiceResult.getSuccessBean(bean);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BaseServiceResult<ImageBean> getImageById_fallback(Integer id, String password) {
        log.info("getImageById方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }


}
