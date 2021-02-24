package xyz.a00000.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.feign.fallback.RegisterFeignFallback;

@Component
@FeignClient(value = "USER-FUNCTION-PROVIDER", qualifier = "registerFeign", path = "/api/user/admin", fallback = RegisterFeignFallback.class)
public interface RegisterFeign {

    @GetMapping("/generateRegisterId")
    BaseActionResult<String> generateRegisterId();

}
