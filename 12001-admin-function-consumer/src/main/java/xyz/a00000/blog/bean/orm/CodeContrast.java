package xyz.a00000.blog.bean.orm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeContrast implements Serializable {

    private Integer id;
    private Integer code;
    private String message;

    public static CodeContrast DEFAULT_SUCCESS_BEAN = new CodeContrast();
    public static CodeContrast DEFAULT_ERROR_BEAN = new CodeContrast();

    static {
        DEFAULT_SUCCESS_BEAN.setCode(0);
        DEFAULT_SUCCESS_BEAN.setMessage("SUCCESS");
        DEFAULT_ERROR_BEAN.setCode(1);
        DEFAULT_ERROR_BEAN.setMessage("UNKNOWN_ERROR");
    }

}
