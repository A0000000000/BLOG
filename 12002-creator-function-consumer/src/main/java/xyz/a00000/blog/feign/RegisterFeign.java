package xyz.a00000.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.dto.RegisterParams;
import xyz.a00000.blog.bean.dto.UserView;
import xyz.a00000.blog.feign.fallback.RegisterFeignFallback;

@Component
@FeignClient(value = "USER-FUNCTION-PROVIDER", qualifier = "registerFeign", path = "/api/user", fallback = RegisterFeignFallback.class)
public interface RegisterFeign {

    @PostMapping("/register")
    BaseActionResult<UserView> register(@RequestBody RegisterParams params);

}
