package xyz.a00000.blog.feign;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.a00000.blog.feign.fallback.ImageFeignFallback;

@Component
@FeignClient(value = "ASYNC-TASK-PROVIDER", qualifier = "imageFeign", path = "/api/async/provider", fallback = ImageFeignFallback.class)
public interface ImageFeign {

    @GetMapping("/getImageById")
    Response getImageById(@RequestParam("id") Integer id, @RequestParam(value = "password", required = false) String password);

    @GetMapping("/downloadImageById")
    Response downloadImageById(@RequestParam("id") Integer id, @RequestParam(value = "password", required = false) String password);

}
