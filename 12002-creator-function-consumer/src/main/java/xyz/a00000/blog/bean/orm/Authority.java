package xyz.a00000.blog.bean.orm;

import lombok.Data;

import java.io.Serializable;

@Data
public class Authority implements Serializable {

    private Integer id;
    private String authorityName;

}
