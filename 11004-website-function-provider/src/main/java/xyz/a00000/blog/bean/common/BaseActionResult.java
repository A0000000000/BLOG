package xyz.a00000.blog.bean.common;

import lombok.Data;
import lombok.NonNull;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.component.ResultCodeTools;

import java.io.Serializable;

@Data
public class BaseActionResult<T> implements Serializable {

    private T data;
    private Integer code;
    private String message;

    public static <T> BaseActionResult<T> from(@NonNull BaseServiceResult<T> source, @NonNull ResultCodeTools tools) {
        BaseActionResult<T> res = new BaseActionResult<>();
        CodeContrast codeContrast = null;
        try {
            codeContrast = tools.getCodeContrast(source.getCode());
        } catch (Exception ignore) { }
        if (codeContrast == null) {
            if (source.getData() == null) {
                codeContrast = CodeContrast.DEFAULT_ERROR_BEAN;
            } else {
                codeContrast = CodeContrast.DEFAULT_SUCCESS_BEAN;
            }
        }
        if (source.getData() != null) {
            res.setData(source.getData());
        }
        res.setCode(codeContrast.getCode());
        res.setMessage(codeContrast.getMessage());
        return res;
    }

}
