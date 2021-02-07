package xyz.a00000.blog.service.imp;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.orm.SystemConfig;
import xyz.a00000.blog.component.SystemConfigTools;
import xyz.a00000.blog.service.AdminService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private RedisTemplate<String, List<String>> redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SystemConfigTools systemConfigTools;

    @Override
    @HystrixCommand(fallbackMethod = "generateRegisterId_fullback",
            commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
    })
    public BaseServiceResult<String> generateRegisterId(String... authority) {
        log.info("开始生成注册的Token");
        List<String> authorities = Arrays.asList(authority);
        String id = passwordEncoder.encode(UUID.randomUUID().toString());
        log.info("注册Token生成成功, Token: " + id);
        ValueOperations<String, List<String>> ops = redisTemplate.opsForValue();
        ops.set(id, authorities);
        SystemConfig config = systemConfigTools.getSystemConfig("register_token_expire");
        log.info("获得Token过期时间(秒): " + config.getValue());
        redisTemplate.expire(id, Long.parseLong(config.getValue()), TimeUnit.SECONDS);
        return BaseServiceResult.getSuccessBean(id);
    }

    public BaseServiceResult<String> generateRegisterId_fullback(String... authority) {
        log.info("generateRegisterId触发熔断, 进入熔断方法, 参数: " + Arrays.toString(authority));
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }


}
