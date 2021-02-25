package xyz.a00000.blog.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;

@Configuration
@Slf4j
public class OpenFeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("通过RequestContextHolder获取ServletRequestAttributes");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        log.info("获取请求对象.");
        HttpServletRequest request = attributes.getRequest();
        log.info("获取请求授权密钥.");
        String authorization = request.getHeader("Authorization");
        log.info("授权密钥: " + authorization);
        if (!StringUtils.isEmpty(attributes)) {
            requestTemplate.header("Authorization", authorization);
        }
    }

}
