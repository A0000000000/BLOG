package xyz.a00000.blog.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.bean.orm.SystemConfig;
import xyz.a00000.blog.feign.SystemConfigFeign;

@Component
@Slf4j
public class SystemConfigFeignFallback implements SystemConfigFeign {

    public static <T> BaseActionResult<T> getFallbackBean() {
        BaseActionResult<T> result = new BaseActionResult<>();
        CodeContrast codeContrast = new CodeContrast(4, 3, "SERVICE_FALLBACK");
        result.setCode(codeContrast.getCode());
        result.setMessage(codeContrast.getMessage());
        return result;
    }

    @Override
    public BaseActionResult<SystemConfig> getSystemConfigById(Integer id) {
        log.info("getSystemConfigById发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<PageBean<SystemConfig>> getSystemConfigByForm(PageForm<SystemConfig> form) {
        log.info("getSystemConfigByForm发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<SystemConfig> createSystemConfig(SystemConfig systemConfig) {
        log.info("createSystemConfig发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<SystemConfig> updateSystemConfig(SystemConfig systemConfig) {
        log.info("updateSystemConfig发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<Void> deleteSystemConfigById(Integer id) {
        log.info("deleteSystemConfigById发生熔断.");
        return getFallbackBean();
    }

}
