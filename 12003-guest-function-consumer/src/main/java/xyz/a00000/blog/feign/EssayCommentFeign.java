package xyz.a00000.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.EssayComment;
import xyz.a00000.blog.feign.fallback.EssayCommentFeignFallback;

@Component
@FeignClient(value = "BLOG-FUNCTION-PROVIDER", qualifier = "essayCommentFeign", path = "/api/blog/essayComment", fallback = EssayCommentFeignFallback.class)
public interface EssayCommentFeign {

    @PostMapping("/addComment")
    BaseActionResult<EssayComment> addComment(@RequestBody EssayComment essayComment);

}
