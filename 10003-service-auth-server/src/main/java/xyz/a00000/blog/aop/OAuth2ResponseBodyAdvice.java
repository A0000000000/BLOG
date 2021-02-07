package xyz.a00000.blog.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.component.ResultCodeTools;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class OAuth2ResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private ResultCodeTools resultCodeTools;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("返回数据为: " + body);
        if (body == null || body.toString().contains("error")) {
            return BaseActionResult.from(body, 2, resultCodeTools);
        } else {
            return BaseActionResult.from(body, 0, resultCodeTools);
        }
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseActionResult<Void> defaultErrorHandler(HttpServletRequest request, Exception e) {
        log.info("访问地址不存在, 构建404返回数据.");
        BaseActionResult<Void> res = new BaseActionResult<>();
        CodeContrast codeContrast;
        if (e instanceof NoHandlerFoundException) {
            codeContrast = resultCodeTools.getCodeContrast(8);
        } else {
            codeContrast = resultCodeTools.getCodeContrast(1);
        }
        if (codeContrast == null) {
            codeContrast = CodeContrast.DEFAULT_ERROR_BEAN;
        }
        res.setCode(codeContrast.getCode());
        res.setMessage(codeContrast.getMessage());
        log.info("构建完成, 返回");
        return res;
    }

}
