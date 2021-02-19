package xyz.a00000.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.feign.fallback.ImageFeignFallback;

@Component
@FeignClient(value = "ASYNC-TASK-PROVIDER", qualifier = "imageFeign", path = "/api/async/image", fallback = ImageFeignFallback.class)
public interface ImageFeign {

    @PostMapping("/deleteImageByEssayId")
    BaseActionResult<Void> deleteImageByEssayId(@RequestParam("essayId") Integer essayId, @RequestParam("authorization") String authorization);

}
