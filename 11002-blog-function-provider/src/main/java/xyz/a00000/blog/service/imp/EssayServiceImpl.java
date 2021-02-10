package xyz.a00000.blog.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import xyz.a00000.blog.bean.cache.EssayInitCache;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.dto.EssayInitResultBean;
import xyz.a00000.blog.bean.dto.EssayInitParamsBean;
import xyz.a00000.blog.bean.orm.Essay;
import xyz.a00000.blog.bean.orm.EssayInfo;
import xyz.a00000.blog.bean.orm.EssayType;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.component.CacheTools;
import xyz.a00000.blog.mapper.EssayInfoMapper;
import xyz.a00000.blog.mapper.EssayMapper;
import xyz.a00000.blog.mapper.EssayTypeMapper;
import xyz.a00000.blog.service.EssayService;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class EssayServiceImpl extends BaseServiceImpl<Essay, EssayMapper> implements EssayService {

    @Autowired
    private EssayTypeMapper essayTypeMapper;
    @Autowired
    private EssayInfoMapper essayInfoMapper;

    @Autowired
    private CacheTools cacheTools;

    @Override
    @HystrixCommand(fallbackMethod = "createEssay_fullback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<EssayInitResultBean> createEssay(EssayInitParamsBean params, UserDetailsBean currentUserDetails) {
        log.info("进入初始化随笔的方法.");
        log.info("检查随笔参数是否正确.");
        if (StringUtils.isEmpty(params.getTitle()) || StringUtils.isEmpty(params.getType())) {
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        log.info("初始化随笔记录.");
        String cacheId = String.format("%d-%d-%s", currentUserDetails.getCreator().getId(), System.currentTimeMillis(), UUID.randomUUID().toString());
        Essay essay = new Essay();
        essay.setTitle(params.getTitle());
        u.insert(essay);
        log.info("插入一条随笔记录.");
        QueryWrapper<EssayType> qwEssayType = new QueryWrapper<>();
        qwEssayType.eq("creator_id", currentUserDetails.getCreator().getId());
        qwEssayType.eq("type_name", params.getType());
        log.info("查询随笔类型是否存在.");
        EssayType type = essayTypeMapper.selectOne(qwEssayType);
        if (type == null) {
            log.info("类型不存在, 准备创建新的类型.");
            type = new EssayType();
            type.setCreatorId(currentUserDetails.getCreator().getId());
            type.setTypeName(params.getType());
            type.setCreateTime(new Date());
            essayTypeMapper.insert(type);
        }
        log.info("创建随笔信息.");
        EssayInfo essayInfo = new EssayInfo();
        essayInfo.setEssayTypeId(type.getId());
        essayInfo.setEssayId(essay.getId());
        essayInfo.setCreateTime(new Date());
        essayInfo.setCreatorId(currentUserDetails.getCreator().getId());
        essayInfo.setPassword(params.getPassword());
        essayInfo.setStar(0);
        essayInfo.setVisitCount(0);
        essayInfoMapper.insert(essayInfo);
        log.info("初始化完成, 返回初始化后的数据.");
        EssayInitCache cache = new EssayInitCache(cacheId, essay, essayInfo);
        log.info("将初始化信息加入缓存.");
        cacheTools.setEssayInitCache(cache);
        return BaseServiceResult.getSuccessBean(new EssayInitResultBean(cacheId, essay.getId(), essayInfo.getId()));
    }

    public BaseServiceResult<EssayInitResultBean> createEssay_fullback(EssayInitParamsBean params, UserDetailsBean currentUserDetails) {
        log.info("initEssay方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FULLBACK"), 3);
    }


    @Override
    @HystrixCommand(fallbackMethod = "updateEssay_fullback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<String> updateEssay(EssayInitParamsBean params, UserDetailsBean currentUserDetails) {
        log.info("进入更新随笔方法.");
        log.info("加载缓存.");
        EssayInitCache essayInitCache = cacheTools.getEssayInitCache(params.getData().getCacheId());
        if (essayInitCache == null) {
            log.info("加载缓存失败, 准备从数据库加载数据.");
            Essay essay = u.selectById(params.getData().getEssayId());
            log.info("判断随笔信息id是否存在来选择加载随笔信息加载方式.");
            if (!StringUtils.isEmpty(params.getData().getEssayInfoId())) {
                EssayInfo essayInfo = essayInfoMapper.selectById(params.getData().getEssayInfoId());
                essayInitCache = new EssayInitCache(null, essay, essayInfo);
            } else {
                QueryWrapper<EssayInfo> qwEssayInfo = new QueryWrapper<>();
                qwEssayInfo.eq("essay_id", params.getData().getEssayId());
                EssayInfo essayInfo = essayInfoMapper.selectOne(qwEssayInfo);
                essayInitCache = new EssayInitCache(null, essay, essayInfo);
            }
        }
        log.info("检查用户是否有权限修改.");
        if (!essayInitCache.getEssayInfo().getCreatorId().equals(currentUserDetails.getCreator().getId())) {
            return BaseServiceResult.getFailedBean(new Exception("ACCESS_DENIED"), 7);
        }
        if (!StringUtils.isEmpty(params.getType())) {
            QueryWrapper<EssayType> qwEssayType = new QueryWrapper<>();
            qwEssayType.eq("creator_id", currentUserDetails.getCreator().getId());
            qwEssayType.eq("type_name", params.getType());
            log.info("查询随笔类型是否存在.");
            EssayType type = essayTypeMapper.selectOne(qwEssayType);
            if (type == null) {
                log.info("类型不存在, 准备创建新的类型.");
                type = new EssayType();
                type.setCreatorId(currentUserDetails.getCreator().getId());
                type.setTypeName(params.getType());
                type.setCreateTime(new Date());
                essayTypeMapper.insert(type);
            }
            log.info("判断随笔类型是否需要修改.");
            if (!type.getId().equals(essayInitCache.getEssayInfo().getEssayTypeId())) {
                log.info("类型需要修改, 重新设置类型.");
                essayInitCache.getEssayInfo().setEssayTypeId(type.getId());
            }
        }
        log.info("判断密码是否需要修改, null为不修改.");
        if (params.getPassword() != null) {
            essayInitCache.getEssayInfo().setPassword(params.getPassword());
        }
        log.info("判断标题是否需要修改.");
        if (!StringUtils.isEmpty(params.getTitle())) {
            essayInitCache.getEssay().setTitle(params.getTitle());
        }
        log.info("设置随笔内容.");
        if (!StringUtils.isEmpty(params.getContent())) {
            essayInitCache.getEssay().setContent(params.getContent());
        }
        log.info("持久化内容和信息.");
        u.updateById(essayInitCache.getEssay());
        essayInfoMapper.updateById(essayInitCache.getEssayInfo());
        return BaseServiceResult.getSuccessBean(String.valueOf(essayInitCache.getEssay().getId()));
    }

    public BaseServiceResult<String> updateEssay_fullback(EssayInitParamsBean params, UserDetailsBean currentUserDetails) {
        log.info("updateEssay方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FULLBACK"), 3);
    }

}
