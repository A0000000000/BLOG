package xyz.a00000.blog.bean.orm;

import lombok.Data;

import java.io.Serializable;

@Data
public class SystemConfig implements Serializable {

    private Integer id;
    private String name;
    private String value;
    private String info;

}
