package xyz.a00000.blog.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.feign.EssayCommentFeign;

@Component
@Slf4j
public class EssayCommentFeignFallback implements EssayCommentFeign {

    public static <T> BaseActionResult<T> getFallbackBean() {
        BaseActionResult<T> result = new BaseActionResult<>();
        CodeContrast codeContrast = new CodeContrast(4, 3, "SERVICE_FALLBACK");
        result.setCode(codeContrast.getCode());
        result.setMessage(codeContrast.getMessage());
        return result;
    }

    @Override
    public BaseActionResult<Void> removeComment(Integer id) {
        log.info("removeComment发生熔断.");
        return getFallbackBean();
    }

}
