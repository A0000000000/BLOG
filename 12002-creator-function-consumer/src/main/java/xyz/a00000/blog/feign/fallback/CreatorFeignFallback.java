package xyz.a00000.blog.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.dto.RegisterParams;
import xyz.a00000.blog.bean.dto.UserView;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.feign.CreatorFeign;

@Component
@Slf4j
public class CreatorFeignFallback implements CreatorFeign {

    public static <T> BaseActionResult<T> getFallbackBean() {
        BaseActionResult<T> result = new BaseActionResult<>();
        CodeContrast codeContrast = new CodeContrast(4, 3, "SERVICE_FALLBACK");
        result.setCode(codeContrast.getCode());
        result.setMessage(codeContrast.getMessage());
        return result;
    }


    @Override
    public BaseActionResult<UserView> updateUserInfo(RegisterParams params) {
        log.info("updateUserInfo发生熔断.");
        return getFallbackBean();
    }
}
