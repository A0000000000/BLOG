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
                .route("async_task_provider_route", r -> r.path("/api/async/**").uri("lb://ASYNC-TASK-PROVIDER"))
                .route("website_function_provider_route", r -> r.path("/api/website/**").uri("lb://WEBSITE-FUNCTION-PROVIDER"))
                .route("admin_function_consumer_route", r -> r.path("/api/admin/**").uri("lb://ADMIN-FUNCTION-CONSUMER"))
                .route("creator_function_consumer_route", r -> r.path("/api/creator/**").uri("lb://CREATOR-FUNCTION-CONSUMER"))
                .route("guest_function_consumer_route", r -> r.path("/api/guest/**").uri("lb://GUEST-FUNCTION-CONSUMER"))
                .build();
    }

}
