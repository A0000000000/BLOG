package xyz.a00000.blog.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.CodeContrast;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class EmailInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("开始判断是否有权限发送邮件.");
        String token = request.getHeader("token");
        String key = request.getHeader("key");
        if(!StringUtils.isEmpty(token) && !StringUtils.isEmpty(key)) {
            return true;
        } else {
            CodeContrast codeContrast = new CodeContrast(6, 5, "EMPTY_ARGS");
            BaseActionResult<Void> result = new BaseActionResult<>();
            result.setCode(codeContrast.getCode());
            result.setMessage(codeContrast.getMessage());
            ObjectMapper mapper = new ObjectMapper();
            String jsonResult = mapper.writeValueAsString(result);
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonResult);
            return false;
        }
    }
}
