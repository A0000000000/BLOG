package xyz.a00000.blog.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Map;

@Configuration
@Slf4j
public class OpenFeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("获取请求密钥.");
        Map<String, Collection<String>> queries = requestTemplate.queries();
        Collection<String> authorizations = queries.get("authorization");
        String authorization = null;
        if (authorizations.size() > 0) {
            authorization = authorizations.stream().findFirst().get();
        }
        if (authorization != null) {
            authorization = authorization.replaceAll("%20", " ");
        }
        log.info("authorization: " + authorization);
        requestTemplate.header("Authorization", authorization);
    }

}
