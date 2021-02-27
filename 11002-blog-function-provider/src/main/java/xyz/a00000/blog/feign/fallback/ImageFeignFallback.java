package xyz.a00000.blog.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.component.ResultCodeTools;
import xyz.a00000.blog.feign.ImageFeign;

@Component
@Slf4j
public class ImageFeignFallback implements ImageFeign {

    @Autowired
    private ResultCodeTools resultCodeTools;

    @Override
    public BaseActionResult<Void> deleteImageByEssayId(Integer essayId) {
        log.info("deleteImageByEssayId发生熔断.");
        BaseServiceResult<Void> result = BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
        return BaseActionResult.from(result, resultCodeTools);
    }

}
