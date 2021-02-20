package xyz.a00000.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import xyz.a00000.blog.aop.EmailInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private EmailInterceptor emailInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(emailInterceptor).addPathPatterns("/api/async/email/**");
        super.addInterceptors(registry);
    }
}
