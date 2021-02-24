package xyz.a00000.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.feign.fallback.CodeContrastFeignFallback;

@Component
@FeignClient(value = "WEBSITE-FUNCTION-PROVIDER", qualifier = "codeContrastFeign", path = "/api/website/codeContrast", fallback = CodeContrastFeignFallback.class)
public interface CodeContrastFeign {

    @GetMapping("/getCodeContrastById/{id}")
    BaseActionResult<CodeContrast> getCodeContrastById(@PathVariable("id") Integer id);

    @PostMapping("/getCodeContrastByForm")
    BaseActionResult<PageBean<CodeContrast>> getCodeContrastByForm(@RequestBody PageForm<CodeContrast> form);

    @PostMapping("createCodeContrast")
    BaseActionResult<CodeContrast> createCodeContrast(@RequestBody CodeContrast codeContrast);

    @PutMapping("/updateCodeContrast")
    BaseActionResult<CodeContrast> updateCodeContrast(@RequestBody CodeContrast codeContrast);

    @DeleteMapping("/deleteCodeContrast/{id}")
    BaseActionResult<Void> deleteCodeContrast(@PathVariable("id") Integer id);

}
