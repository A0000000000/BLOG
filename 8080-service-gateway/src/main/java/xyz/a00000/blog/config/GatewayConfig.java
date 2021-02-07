package xyz.a00000.blog.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth_server_route", r -> r.path("/api/oauth/**").uri("lb://SERVICE-AUTH-SERVER"))
                .route("user_function_provider_route", r -> r.path("/api/user/**").uri("lb://USER-FUNCTION-PROVIDER"))
                .route("blog_function_provider_route", r -> r.path("/api/blog/**").uri("lb://BLOG-FUNCTION-PROVIDER"))
                .build();
    }

}
