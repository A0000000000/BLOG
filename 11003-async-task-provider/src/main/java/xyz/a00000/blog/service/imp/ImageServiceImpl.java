package xyz.a00000.blog.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.ImageBean;
import xyz.a00000.blog.bean.orm.EssayInfo;
import xyz.a00000.blog.bean.orm.Image;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.mapper.EssayInfoMapper;
import xyz.a00000.blog.mapper.ImageMapper;
import xyz.a00000.blog.service.ImageService;
import xyz.a00000.blog.service.mq.RabbitMQProvider;

import java.util.Date;
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

    @Override
    @HystrixCommand(fallbackMethod = "uploadImage_fullback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<Image> uploadImage(MultipartFile image, Integer essayId, String password, UserDetailsBean currentUserDetails) {
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
            log.info("生成随机的文件名.");
            String filename = UUID.randomUUID().toString().replaceAll("-", "");
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
            log.info("保存完成, 准备返回数据.");
            return BaseServiceResult.getSuccessBean(imageData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BaseServiceResult<Image> uploadImage_fullback(MultipartFile image, Integer essayId, String password, UserDetailsBean currentUserDetails) {
        log.info("uploadImage方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FULLBACK"), 3);
    }

    @Override
    @HystrixCommand(fallbackMethod = "deleteImage_fullback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<Void> deleteImage(Integer id, UserDetailsBean currentUserDetails) {
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

    public BaseServiceResult<Void> deleteImage_fullback(Integer id, UserDetailsBean currentUserDetails) {
        log.info("deleteImage方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FULLBACK"), 3);
    }

}
