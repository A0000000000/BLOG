package xyz.a00000.blog.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.feign.OAuthFeign;

import java.util.Map;

@Component
@Slf4j
public class OAuthFeignFallback implements OAuthFeign {

    public static <T> BaseActionResult<T> getFallbackBean() {
        BaseActionResult<T> result = new BaseActionResult<>();
        CodeContrast codeContrast = new CodeContrast(4, 3, "SERVICE_FALLBACK");
        result.setCode(codeContrast.getCode());
        result.setMessage(codeContrast.getMessage());
        return result;
    }

    @Override
    public BaseActionResult<Map<String, Object>> login(String clientId, String clientSecret, String grantType, String username, String password) {
        log.info("login方法发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<Map<String, Object>> refresh(String clientId, String clientSecret, String grantType, String token) {
        log.info("refresh方法发生熔断.");
        return getFallbackBean();
    }
}
