package xyz.a00000.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.feign.fallback.OAuthFeignFallback;

import java.util.Map;

@Component
@FeignClient(value = "SERVICE-AUTH-SERVER", qualifier = "oAuthFeign", path = "/api/oauth", fallback = OAuthFeignFallback.class)
public interface OAuthFeign {

    @PostMapping("/token")
    BaseActionResult<Map<String, Object>> login(@RequestParam("client_id") String clientId, @RequestParam("client_secret") String clientSecret, @RequestParam("grant_type") String grantType, @RequestParam("username") String username, @RequestParam("password") String password);

    @PostMapping("/token")
    BaseActionResult<Map<String, Object>> refresh(@RequestParam("client_id") String clientId, @RequestParam("client_secret") String clientSecret, @RequestParam("grant_type") String grantType, @RequestParam("refresh_token") String token);

}
