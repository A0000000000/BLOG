package xyz.a00000.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
public class CreatorFunctionConsumer {

    public static void main(String[] args) {
        SpringApplication.run(CreatorFunctionConsumer.class, args);
    }

}
