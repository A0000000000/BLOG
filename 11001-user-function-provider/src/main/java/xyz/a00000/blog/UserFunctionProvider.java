package xyz.a00000.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
@MapperScan(basePackages = "xyz.a00000.blog.mapper")
public class UserFunctionProvider {
    public static void main(String[] args) {
        SpringApplication.run(UserFunctionProvider.class, args);
    }
}
