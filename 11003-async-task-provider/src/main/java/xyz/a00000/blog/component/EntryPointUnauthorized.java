package xyz.a00000.blog.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.CodeContrast;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class EntryPointUnauthorized implements AuthenticationEntryPoint {

    @Autowired
    private ResultCodeTools resultCodeTools;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        BaseActionResult<Object> action = new BaseActionResult<>();
        CodeContrast codeContrast = resultCodeTools.getCodeContrast(2);
        action.setCode(codeContrast.getCode());
        action.setMessage(codeContrast.getMessage());
        action.setData(e.getCause());
        ObjectMapper mapper = new ObjectMapper();
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        try (ServletOutputStream outputStream = httpServletResponse.getOutputStream()) {
            outputStream.write(mapper.writeValueAsString(action).getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }
    }
}
