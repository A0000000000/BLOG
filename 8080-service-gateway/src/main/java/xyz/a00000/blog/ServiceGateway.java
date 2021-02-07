package xyz.a00000.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceGateway {
    public static void main(String[] args) {
        SpringApplication.run(ServiceGateway.class, args);
    }
}
