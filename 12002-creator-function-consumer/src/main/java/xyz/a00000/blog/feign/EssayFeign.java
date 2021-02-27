package xyz.a00000.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.dto.EssayInitParamsBean;
import xyz.a00000.blog.bean.dto.EssayInitResultBean;
import xyz.a00000.blog.feign.fallback.EssayFeignFallback;

@Component
@FeignClient(value = "BLOG-FUNCTION-PROVIDER", qualifier = "essayFeign", path = "/api/blog/essay", fallback = EssayFeignFallback.class)
public interface EssayFeign {

    @PostMapping("/createEssay")
    BaseActionResult<EssayInitResultBean> createEssay(@RequestBody EssayInitParamsBean params);

    @PutMapping("/updateEssay")
    BaseActionResult<String> updateEssay(@RequestBody EssayInitParamsBean params);

    @DeleteMapping("/deleteEssayById/{id}")
    BaseActionResult<Void> deleteEssayById(@PathVariable("id") Integer id);

}
