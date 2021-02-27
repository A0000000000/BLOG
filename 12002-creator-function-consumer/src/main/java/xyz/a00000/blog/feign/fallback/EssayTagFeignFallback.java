package xyz.a00000.blog.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.dto.EssayTagParams;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.bean.orm.EssayTag;
import xyz.a00000.blog.feign.EssayTagFeign;

@Component
@Slf4j
public class EssayTagFeignFallback implements EssayTagFeign {

    public static <T> BaseActionResult<T> getFallbackBean() {
        BaseActionResult<T> result = new BaseActionResult<>();
        CodeContrast codeContrast = new CodeContrast(4, 3, "SERVICE_FALLBACK");
        result.setCode(codeContrast.getCode());
        result.setMessage(codeContrast.getMessage());
        return result;
    }

    @Override
    public BaseActionResult<EssayTag> addNewTag(EssayTagParams params) {
        log.info("addNewTag发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<Void> removeTag(Integer id) {
        log.info("removeTag发生熔断.");
        return getFallbackBean();
    }


}
