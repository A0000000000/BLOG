package xyz.a00000.blog.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    private String endpoint;
    private String accessKey;
    private String secretKey;

    @Bean
    public MinioClient getMinioClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(endpoint, accessKey, secretKey);
    }

}
