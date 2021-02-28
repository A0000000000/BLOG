package xyz.a00000.blog.bean.orm;

import lombok.Data;

import java.io.Serializable;

@Data
public class Essay implements Serializable {

    private Integer id;
    private String title;
    private String content;

}
