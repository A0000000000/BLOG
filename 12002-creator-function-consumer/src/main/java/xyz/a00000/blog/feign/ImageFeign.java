package xyz.a00000.blog.feign;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.Image;
import xyz.a00000.blog.feign.fallback.ImageFeignFallback;

import java.util.List;

@Component
@FeignClient(value = "ASYNC-TASK-PROVIDER", qualifier = "imageFeign", configuration = ImageFeign.FileConfig.class, path = "/api/async/image", fallback = ImageFeignFallback.class)
public interface ImageFeign {

    @PostMapping(value = "/uploadImage", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    BaseActionResult<List<Image>> uploadImage(@RequestPart(value = "images", required = false) MultipartFile[] images, @RequestParam("essayId") Integer essayId, @RequestParam(value = "password", required = false) String password);

    @DeleteMapping("/deleteImageById/{id}")
    BaseActionResult<Void> deleteImageById(@PathVariable("id") Integer id);

    @DeleteMapping("/deleteImageByEssayId/{id}")
    BaseActionResult<Void> deleteImageByEssayId(@PathVariable("id") Integer essayId);

    class FileConfig {

        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder();
        }

    }

}
