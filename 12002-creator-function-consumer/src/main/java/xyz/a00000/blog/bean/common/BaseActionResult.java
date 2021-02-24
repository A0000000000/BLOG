package xyz.a00000.blog.bean.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseActionResult<T> implements Serializable {

    private T data;
    private Integer code;
    private String message;


}
