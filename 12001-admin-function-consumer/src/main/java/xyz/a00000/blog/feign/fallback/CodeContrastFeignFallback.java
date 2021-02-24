package xyz.a00000.blog.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.feign.CodeContrastFeign;

@Component
@Slf4j
public class CodeContrastFeignFallback implements CodeContrastFeign {

    public static <T> BaseActionResult<T> getFallbackBean() {
        BaseActionResult<T> result = new BaseActionResult<>();
        CodeContrast codeContrast = new CodeContrast(4, 3, "SERVICE_FALLBACK");
        result.setCode(codeContrast.getCode());
        result.setMessage(codeContrast.getMessage());
        return result;
    }

    @Override
    public BaseActionResult<CodeContrast> getCodeContrastById(Integer id) {
        log.info("getCodeContrastById发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<PageBean<CodeContrast>> getCodeContrastByForm(PageForm<CodeContrast> form) {
        log.info("getCodeContrastByForm发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<CodeContrast> createCodeContrast(CodeContrast codeContrast) {
        log.info("createCodeContrast发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<CodeContrast> updateCodeContrast(CodeContrast codeContrast) {
        log.info("updateCodeContrast发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<Void> deleteCodeContrast(Integer id) {
        log.info("deleteCodeContrast发生熔断.");
        return getFallbackBean();
    }


}
