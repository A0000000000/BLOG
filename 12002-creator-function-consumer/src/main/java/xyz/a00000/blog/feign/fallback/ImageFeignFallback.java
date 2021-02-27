package xyz.a00000.blog.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.bean.orm.Image;
import xyz.a00000.blog.feign.ImageFeign;

import java.util.List;

@Component
@Slf4j
public class ImageFeignFallback implements ImageFeign {

    public static <T> BaseActionResult<T> getFallbackBean() {
        BaseActionResult<T> result = new BaseActionResult<>();
        CodeContrast codeContrast = new CodeContrast(4, 3, "SERVICE_FALLBACK");
        result.setCode(codeContrast.getCode());
        result.setMessage(codeContrast.getMessage());
        return result;
    }


    @Override
    public BaseActionResult<List<Image>> uploadImage(MultipartFile[] images, Integer essayId, String password) {
        log.info("uploadImage发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<Void> deleteImageById(Integer id) {
        log.info("deleteImageById发生熔断.");
        return getFallbackBean();
    }

    @Override
    public BaseActionResult<Void> deleteImageByEssayId(Integer essayId) {
        log.info("deleteImageByEssayId发生熔断.");
        return getFallbackBean();
    }

}
