package xyz.a00000.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.dto.EssayTagParams;
import xyz.a00000.blog.bean.orm.EssayTag;

@Component
@FeignClient(value = "BLOG-FUNCTION-PROVIDER", qualifier = "essayTagFeign", path = "/api/blog/essayTag")
public interface EssayTagFeign {

    @PostMapping("/addNewTag")
    BaseActionResult<EssayTag> addNewTag(@RequestBody EssayTagParams params);

    @DeleteMapping("/removeTag/{id}")
    BaseActionResult<Void> removeTag(@PathVariable("id") Integer id);

}
