package xyz.a00000.blog.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.AccessInfo;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.feign.AccessInfoFeign;

@Component
@Slf4j
public class AccessInfoFeignFallback implements AccessInfoFeign {

    public static <T> BaseActionResult<T> getFallbackBean() {
        BaseActionResult<T> result = new BaseActionResult<>();
        CodeContrast codeContrast = new CodeContrast(4, 3, "SERVICE_FALLBACK");
        result.setCode(codeContrast.getCode());
        result.setMessage(codeContrast.getMessage());
        return result;
    }

    @Override
    public BaseActionResult<AccessInfo> getAccessInfoById(Integer id) {
        log.info("getAccessInfoById发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<PageBean<AccessInfo>> getAccessInfoByForm(PageForm<AccessInfo> form) {
        log.info("getAccessInfoByForm发生熔断.");
        return getFallbackBean();
    }


}
