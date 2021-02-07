package xyz.a00000.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "xyz.a00000.blog.mapper")
public class AuthServer {
    public static void main(String[] args) {
        SpringApplication.run(AuthServer.class, args);
    }
}
