package xyz.a00000.blog.bean.common;


import lombok.Data;

import java.io.Serializable;

@Data
public class PageForm<T> implements Serializable {

    private Integer page;
    private Integer count;
    private T conditions;

}
