package xyz.a00000.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.EssayType;
import xyz.a00000.blog.feign.fallback.EssayTypeFeignFallback;

import java.util.List;

@Component
@FeignClient(value = "BLOG-FUNCTION-PROVIDER", qualifier = "essayTypeFeign", path = "/api/blog/essayType", fallback = EssayTypeFeignFallback.class)
public interface EssayTypeFeign {

    @GetMapping("/getAllEssayType")
    BaseActionResult<List<EssayType>> getAllEssayType();

    @PostMapping("/getEssayTypeByForm")
    BaseActionResult<PageBean<EssayType>> getEssayTypeByForm(@RequestBody PageForm<EssayType> form);

    @PostMapping("/createEssayType")
    BaseActionResult<EssayType> createEssayType(@RequestBody EssayType type);

    @PutMapping("/updateEssayType")
    BaseActionResult<EssayType> updateEssayType(@RequestBody EssayType type);

    @DeleteMapping("/deleteEssayTypeById/{id}")
    BaseActionResult<Void> deleteEssayTypeById(@PathVariable("id") Integer id);

}
