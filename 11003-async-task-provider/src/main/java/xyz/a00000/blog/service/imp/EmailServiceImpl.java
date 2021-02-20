package xyz.a00000.blog.service.imp;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.EmailBean;
import xyz.a00000.blog.bean.orm.Email;
import xyz.a00000.blog.mapper.EmailMapper;
import xyz.a00000.blog.service.EmailService;
import xyz.a00000.blog.service.mq.RabbitMQProvider;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public
class EmailServiceImpl extends BaseServiceImpl<Email, EmailMapper> implements EmailService {

    @Autowired
    private RabbitMQProvider rabbitMQProvider;
    @Autowired
    private RedisTemplate<String, EmailBean> redisTemplate;


    @Override
    @HystrixCommand(fallbackMethod = "sendEmail_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<Email> sendEmail(Email email) {
        log.info("准备发送邮件.");
        log.info("校验参数.");
        if (email == null || email.getReceiver() == null) {
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        log.info("构建发送邮件的bean");
        EmailBean bean = new EmailBean(email);
        ValueOperations<String, EmailBean> ops = redisTemplate.opsForValue();
        log.info("生成发送邮件的key.");
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        while (redisTemplate.hasKey(key)) {
            key = UUID.randomUUID().toString().replaceAll("-", "");
        }
        log.info("将数据保存至Redis.");
        ops.set(key, bean);
        log.info("将任务发送至消息队列.");
        rabbitMQProvider.sendEmail(key);
        log.info("准备将记录保存至数据库.");
        email.setSendTime(new Date());
        u.insert(email);
        log.info("操作完成, 准备返回.");
        return BaseServiceResult.getSuccessBean(email);
    }

    public BaseServiceResult<Email> sendEmail_fallback(Email email) {
        log.info("sendEmail方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

}
