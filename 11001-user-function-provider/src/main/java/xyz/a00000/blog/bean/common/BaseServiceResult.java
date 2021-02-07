package xyz.a00000.blog.bean.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseServiceResult<T> implements Serializable {

    private T data;
    private Exception e;
    private Integer code;

    public static<T> BaseServiceResult<T> getSuccessBean(T data) {
        return new BaseServiceResult<T>(data, null, 0);
    }

    public static<T> BaseServiceResult<T> getFailedBean(Exception e, Integer code) {
        return new BaseServiceResult<>(null, e, code);
    }

}
