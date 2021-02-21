package xyz.a00000.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.dto.EssayTagParams;
import xyz.a00000.blog.bean.orm.EssayEssayTag;
import xyz.a00000.blog.bean.orm.EssayInfo;
import xyz.a00000.blog.bean.orm.EssayTag;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.mapper.EssayEssayTagMapper;
import xyz.a00000.blog.mapper.EssayInfoMapper;
import xyz.a00000.blog.mapper.EssayTagMapper;
import xyz.a00000.blog.service.EssayTagService;

import java.util.Date;

@Service
@Transactional
@Slf4j
public class EssayTagServiceImpl extends BaseServiceImpl<EssayTag, EssayTagMapper> implements EssayTagService {

    @Autowired
    private EssayInfoMapper essayInfoMapper;
    @Autowired
    private EssayEssayTagMapper essayEssayTagMapper;

    @Override
    @HystrixCommand(fallbackMethod = "addNewTag_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<EssayTag> addNewTag(EssayTagParams params, UserDetailsBean currentUserDetails) {
        log.info("向随笔添加标签.");
        log.info("检查参数是否合法.");
        if (params.getEssayId() == null || StringUtils.isEmpty(params.getTagName())) {
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        log.info("检查是否有权限添加.");
        QueryWrapper<EssayInfo> qwEssayInfo = new QueryWrapper<>();
        qwEssayInfo.eq("essay_id", params.getEssayId());
        qwEssayInfo.eq("creator_id", currentUserDetails.getCreator().getId());
        EssayInfo essayInfo = essayInfoMapper.selectOne(qwEssayInfo);
        if (essayInfo == null) {
            return BaseServiceResult.getFailedBean(new Exception("ACCESS_DENIED"), 7);
        }
        log.info("检查标签是否已经存在.");
        QueryWrapper<EssayTag> qwEssayTag = new QueryWrapper<>();
        qwEssayTag.eq("creator_id", currentUserDetails.getCreator().getId());
        qwEssayTag.eq("tag_name", params.getTagName());
        EssayTag essayTag = u.selectOne(qwEssayTag);
        if (essayTag == null) {
            log.info("标签不存在, 创建标签.");
            essayTag = new EssayTag();
            essayTag.setCreatorId(currentUserDetails.getCreator().getId());
            essayTag.setTagName(params.getTagName());
            essayTag.setCreateTime(new Date());
            u.insert(essayTag);
        }
        log.info("查询标签是否已经存在.");
        QueryWrapper<EssayEssayTag> qwEssayEssayTag = new QueryWrapper<>();
        qwEssayEssayTag.eq("essay_tag_id", essayTag.getId());
        qwEssayEssayTag.eq("essay_id", params.getEssayId());
        EssayEssayTag essayEssayTag = essayEssayTagMapper.selectOne(qwEssayEssayTag);
        if (essayEssayTag == null) {
            essayEssayTag = new EssayEssayTag();
            essayEssayTag.setEssayId(params.getEssayId());
            essayEssayTag.setEssayTagId(essayTag.getId());
            essayEssayTag.setCreateTime(new Date());
            essayEssayTagMapper.insert(essayEssayTag);
        }
        return BaseServiceResult.getSuccessBean(essayTag);
    }

    public BaseServiceResult<EssayTag> addNewTag_fallback(EssayTagParams params, UserDetailsBean currentUserDetails) {
        log.info("addNewTag方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

    @Override
    @HystrixCommand(fallbackMethod = "removeTag_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<Void> removeTag(EssayEssayTag essayEssayTag, UserDetailsBean currentUserDetails) {
        log.info("删除一条标签记录.");
        log.info("检查参数是否合法.");
        if (essayEssayTag.getId() == null) {
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        log.info("加载待删除的完整信息.");
        essayEssayTag = essayEssayTagMapper.selectById(essayEssayTag.getId());
        log.info("检查操作是否有权限.");
        QueryWrapper<EssayInfo> qwEssayInfo = new QueryWrapper<>();
        qwEssayInfo.eq("creator_id", currentUserDetails.getCreator().getId());
        qwEssayInfo.eq("essay_id", essayEssayTag.getEssayId());
        EssayInfo essayInfo = essayInfoMapper.selectOne(qwEssayInfo);
        if (essayInfo == null) {
            return BaseServiceResult.getFailedBean(new Exception("ACCESS_DENIED"), 7);
        }
        log.info("删除标签.");
        essayEssayTagMapper.deleteById(essayEssayTag.getId());
        log.info("判断标签记录是否需要删除.");
        QueryWrapper<EssayEssayTag> qwEssayEssayTag = new QueryWrapper<>();
        qwEssayEssayTag.eq("essay_tag_id", essayEssayTag.getEssayTagId());
        Integer num = essayEssayTagMapper.selectCount(qwEssayEssayTag);
        if (num == null || num.intValue() == 0) {
            log.info("已经没有随笔使用该标签, 从数据库删除该标签.");
            u.deleteById(essayEssayTag.getEssayTagId());
        }
        return BaseServiceResult.getSuccessBean(null);
    }

    public BaseServiceResult<Void> removeTag_fallback(EssayEssayTag essayEssayTag, UserDetailsBean currentUserDetails) {
        log.info("removeTag方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

}
