package xyz.a00000.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.dto.RegisterParams;
import xyz.a00000.blog.bean.dto.UserView;
import xyz.a00000.blog.feign.fallback.CreatorFeignFallback;

@Component
@FeignClient(value = "USER-FUNCTION-PROVIDER", qualifier = "creatorFeign", path = "/api/user/creator", fallback = CreatorFeignFallback.class)
public interface CreatorFeign {

    @PutMapping("/updateUserInfo")
    BaseActionResult<UserView> updateUserInfo(@RequestBody RegisterParams params);

}
