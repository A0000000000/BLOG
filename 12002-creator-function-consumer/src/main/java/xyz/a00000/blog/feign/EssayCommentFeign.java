package xyz.a00000.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.feign.fallback.EssayCommentFeignFallback;

@Component
@FeignClient(value = "BLOG-FUNCTION-PROVIDER", qualifier = "essayCommentFeign", path = "/api/blog/essayComment", fallback = EssayCommentFeignFallback.class)
public interface EssayCommentFeign {

    @DeleteMapping("/removeComment/{id}")
    BaseActionResult<Void> removeComment(@PathVariable("id") Integer id);

}
