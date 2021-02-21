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
import xyz.a00000.blog.bean.orm.EssayComment;
import xyz.a00000.blog.bean.orm.EssayInfo;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.mapper.EssayCommentMapper;
import xyz.a00000.blog.mapper.EssayInfoMapper;
import xyz.a00000.blog.service.EssayCommentService;

import java.util.Date;

@Service
@Slf4j
@Transactional
public class EssayCommentServiceImpl extends BaseServiceImpl<EssayComment, EssayCommentMapper> implements EssayCommentService {

    @Autowired
    private EssayInfoMapper essayInfoMapper;

    @Override
    @HystrixCommand(fallbackMethod = "addComment_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<EssayComment> addComment(EssayComment essayComment) {
        log.info("添加一条评论.");
        log.info("进行参数校验.");
        if (StringUtils.isEmpty(essayComment.getNickname()) || StringUtils.isEmpty(essayComment.getMessage()) || essayComment.getEssayId() == null) {
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        log.info("添加额外信息, 准备持久化数据.");
        essayComment.setCommentTime(new Date());
        u.insert(essayComment);
        return BaseServiceResult.getSuccessBean(essayComment);
    }

    public BaseServiceResult<EssayComment> addComment_fallback(EssayComment essayComment) {
        log.info("addComment方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

    @Override
    @HystrixCommand(fallbackMethod = "removeComment_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<Void> removeComment(EssayComment essayComment, UserDetailsBean currentUserDetails) {
        log.info("检查参数是否合法.");
        if (essayComment.getId() == null) {
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        log.info("加载完整的数据.");
        essayComment = u.selectById(essayComment.getId());
        log.info("判断是否有权限删除.");
        if (essayComment == null) {
            return BaseServiceResult.getFailedBean(new Exception("ACCESS_DENIED"), 7);
        }
        QueryWrapper<EssayInfo> qwEssayInfo = new QueryWrapper<>();
        qwEssayInfo.eq("essay_id", essayComment.getEssayId());
        EssayInfo essayInfo = essayInfoMapper.selectOne(qwEssayInfo);
        if (!essayInfo.getCreatorId().equals(currentUserDetails.getCreator().getId())) {
            return BaseServiceResult.getFailedBean(new Exception("ACCESS_DENIED"), 7);
        }
        u.deleteById(essayComment.getId());
        return BaseServiceResult.getSuccessBean(null);
    }

    public BaseServiceResult<Void> removeComment_fallback(EssayComment essayComment, UserDetailsBean currentUserDetails) {
        log.info("removeComment方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

}
