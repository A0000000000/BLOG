package xyz.a00000.blog.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.dto.EssayInitParamsBean;
import xyz.a00000.blog.bean.dto.EssayInitResultBean;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.feign.EssayFeign;

@Component
@Slf4j
public class EssayFeignFallback implements EssayFeign {

    public static <T> BaseActionResult<T> getFallbackBean() {
        BaseActionResult<T> result = new BaseActionResult<>();
        CodeContrast codeContrast = new CodeContrast(4, 3, "SERVICE_FALLBACK");
        result.setCode(codeContrast.getCode());
        result.setMessage(codeContrast.getMessage());
        return result;
    }

    @Override
    public BaseActionResult<EssayInitResultBean> createEssay(EssayInitParamsBean params) {
        log.info("createEssay发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<String> updateEssay(EssayInitParamsBean params) {
        log.info("updateEssay发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<Void> deleteEssayById(Integer id) {
        log.info("deleteEssayById发生熔断.");
        return getFallbackBean();
    }


}
