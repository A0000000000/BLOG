package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.a00000.blog.bean.common.EmailBean;
import xyz.a00000.blog.service.mq.RabbitMQProvider;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/async/email")
public class EmailController {

    @Autowired
    private RabbitMQProvider rabbitMQProvider;
    @Autowired
    private RedisTemplate<String, EmailBean> redisTemplate;

    @GetMapping
    public Object demo() {
        EmailBean bean = new EmailBean("sender", "receiver", "AAA");
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        ValueOperations<String, EmailBean> ops = redisTemplate.opsForValue();
        ops.set(id, bean);
        rabbitMQProvider.sendEmail(id);
        return bean;
    }

}
