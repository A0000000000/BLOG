package xyz.a00000.blog.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.bean.orm.EssayType;
import xyz.a00000.blog.feign.EssayTypeFeign;

import java.util.List;

@Component
@Slf4j
public class EssayTypeFeignFallback implements EssayTypeFeign {

    public static <T> BaseActionResult<T> getFallbackBean() {
        BaseActionResult<T> result = new BaseActionResult<>();
        CodeContrast codeContrast = new CodeContrast(4, 3, "SERVICE_FALLBACK");
        result.setCode(codeContrast.getCode());
        result.setMessage(codeContrast.getMessage());
        return result;
    }

    @Override
    public BaseActionResult<List<EssayType>> getAllEssayType() {
        log.info("getAllEssayType发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<PageBean<EssayType>> getEssayTypeByForm(PageForm<EssayType> form) {
        log.info("getEssayType发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<EssayType> createEssayType(EssayType type) {
        log.info("createEssayType发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<EssayType> updateEssayType(EssayType type) {
        log.info("updateEssayType发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<Void> deleteEssayTypeById(Integer id) {
        log.info("deleteEssayType发生熔断.");
        return getFallbackBean();
    }
}
