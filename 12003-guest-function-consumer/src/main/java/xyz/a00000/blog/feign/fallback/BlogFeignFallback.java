package xyz.a00000.blog.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.dto.EssayProxyBean;
import xyz.a00000.blog.bean.dto.EssayQueryBean;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.bean.orm.EssayComment;
import xyz.a00000.blog.bean.orm.EssayInfo;
import xyz.a00000.blog.bean.orm.EssayTag;
import xyz.a00000.blog.feign.BlogFeign;

import java.util.List;

@Component
@Slf4j
public class BlogFeignFallback implements BlogFeign {

    public static <T> BaseActionResult<T> getFallbackBean() {
        BaseActionResult<T> result = new BaseActionResult<>();
        CodeContrast codeContrast = new CodeContrast(4, 3, "SERVICE_FALLBACK");
        result.setCode(codeContrast.getCode());
        result.setMessage(codeContrast.getMessage());
        return result;
    }

    @Override
    public BaseActionResult<PageBean<EssayQueryBean>> getEssayList(PageForm<EssayInfo> form) {
        log.info("getEssayList发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<EssayProxyBean> getEssayData(Integer essayId, String password) {
        log.info("getEssayData发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<List<EssayTag>> getEssayTags(Integer essayId) {
        log.info("getEssayTags发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<PageBean<EssayComment>> getEssayComments(PageForm<EssayComment> form) {
        log.info("getEssayComments发生熔断.");
        return getFallbackBean();
    }
}
