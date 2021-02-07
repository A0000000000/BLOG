package xyz.a00000.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
@MapperScan(basePackages = "xyz.a00000.blog.mapper")
public class BlogFunctionProvider {
    public static void main(String[] args) {
        SpringApplication.run(BlogFunctionProvider.class, args);
    }
}
