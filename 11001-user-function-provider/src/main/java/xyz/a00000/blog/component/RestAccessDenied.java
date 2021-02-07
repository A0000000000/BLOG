package xyz.a00000.blog.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.CodeContrast;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class RestAccessDenied implements AccessDeniedHandler {

    @Autowired
    private ResultCodeTools resultCodeTools;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        BaseActionResult<Object> action = new BaseActionResult<>();
        CodeContrast codeContrast = resultCodeTools.getCodeContrast(7);
        ObjectMapper mapper = new ObjectMapper();
        action.setCode(codeContrast.getCode());
        action.setMessage(codeContrast.getMessage());
        action.setData(e.getCause());
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        try (ServletOutputStream outputStream = httpServletResponse.getOutputStream()) {
            outputStream.write(mapper.writeValueAsString(action).getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }
    }
}
