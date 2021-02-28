package xyz.a00000.blog.feign.fallback;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.feign.ImageFeign;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
    public Response getImageById(Integer id, String password) {
        log.info("getImageById发生熔断.");
        Map<String, Collection<String>> m = new HashMap<>();
        m.put("content-type", Arrays.asList("application/json;charset=utf8"));
        BaseActionResult<Object> bean = getFallbackBean();
        ObjectMapper mapper = new ObjectMapper();
        String jsonData = "";
        try {
            jsonData = mapper.writeValueAsString(bean);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        return Response.builder().headers(m).body(jsonData.getBytes(StandardCharsets.UTF_8)).build();
    }

    @Override
    public Response downloadImageById(Integer id, String password) {
        log.info("downloadImageById发生熔断.");
        Map<String, Collection<String>> m = new HashMap<>();
        m.put("content-type", Arrays.asList("application/json;charset=utf8"));
        BaseActionResult<Object> bean = getFallbackBean();
        ObjectMapper mapper = new ObjectMapper();
        String jsonData = "";
        try {
            jsonData = mapper.writeValueAsString(bean);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        return Response.builder().headers(m).body(jsonData.getBytes(StandardCharsets.UTF_8)).build();    }
}
