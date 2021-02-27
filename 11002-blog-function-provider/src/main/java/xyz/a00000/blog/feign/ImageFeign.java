package xyz.a00000.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.feign.fallback.ImageFeignFallback;

@Component
@FeignClient(value = "ASYNC-TASK-PROVIDER", qualifier = "imageFeign", path = "/api/async/image", fallback = ImageFeignFallback.class)
public interface ImageFeign {

    @DeleteMapping("/deleteImageByEssayId/{id}")
    BaseActionResult<Void> deleteImageByEssayId(@PathVariable("id") Integer essayId);

}
