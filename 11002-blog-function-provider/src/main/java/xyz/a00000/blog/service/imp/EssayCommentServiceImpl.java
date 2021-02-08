package xyz.a00000.blog.service.imp;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.orm.EssayComment;
import xyz.a00000.blog.mapper.EssayCommentMapper;
import xyz.a00000.blog.service.EssayCommentService;

import java.util.Date;

@Service
@Slf4j
@Transactional
public class EssayCommentServiceImpl extends BaseServiceImpl<EssayComment, EssayCommentMapper> implements EssayCommentService {

    @Override
    @HystrixCommand(fallbackMethod = "addComment_fullback",
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

    public BaseServiceResult<EssayComment> addComment_fullback(EssayComment essayComment) {
        log.info("addComment方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

}
