package xyz.a00000.blog.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.CodeContrast;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseActionResult<Object> defaultErrorHandler(HttpServletRequest request, Exception e) {
        log.info("访问地址不存在, 构建404返回数据.");
        BaseActionResult<Object> res = new BaseActionResult<>();
        CodeContrast codeContrast = null;
        if (e instanceof NoHandlerFoundException) {
            codeContrast = new CodeContrast(9, 8, "NOT_FOUND");
        } else if (e instanceof InvalidTokenException) {
            codeContrast = new CodeContrast(5, 4, "INVALID_TOKEN");
        } else {
            codeContrast = CodeContrast.DEFAULT_ERROR_BEAN;
        }
        res.setCode(codeContrast.getCode());
        res.setMessage(codeContrast.getMessage());
        res.setData(e);
        log.info("构建完成, 返回");
        return res;
    }

}
