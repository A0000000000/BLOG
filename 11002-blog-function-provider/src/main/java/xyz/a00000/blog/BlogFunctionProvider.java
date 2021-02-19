package xyz.a00000.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@MapperScan(basePackages = "xyz.a00000.blog.mapper")
@EnableFeignClients
public class BlogFunctionProvider {
    public static void main(String[] args) {
        SpringApplication.run(BlogFunctionProvider.class, args);
    }
}
