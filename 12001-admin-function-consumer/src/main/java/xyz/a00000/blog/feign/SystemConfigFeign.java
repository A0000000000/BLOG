package xyz.a00000.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.SystemConfig;
import xyz.a00000.blog.feign.fallback.SystemConfigFeignFallback;

@Component
@FeignClient(value = "WEBSITE-FUNCTION-PROVIDER", qualifier = "systemConfigFeign", path = "/api/website/systemConfig", fallback = SystemConfigFeignFallback.class)
public interface SystemConfigFeign {

    @GetMapping("/getSystemConfigById/{id}")
    BaseActionResult<SystemConfig> getSystemConfigById(@PathVariable("id") Integer id);

    @PostMapping("/getSystemConfigByForm")
    BaseActionResult<PageBean<SystemConfig>> getSystemConfigByForm(@RequestBody PageForm<SystemConfig> form);

    @PostMapping("/createSystemConfig")
    BaseActionResult<SystemConfig> createSystemConfig(@RequestBody SystemConfig systemConfig);

    @PutMapping("/updateSystemConfig")
    BaseActionResult<SystemConfig> updateSystemConfig(@RequestBody SystemConfig systemConfig);

    @DeleteMapping("/deleteSystemConfigById/{id}")
    BaseActionResult<Void> deleteSystemConfigById(@PathVariable("id") Integer id);

}
