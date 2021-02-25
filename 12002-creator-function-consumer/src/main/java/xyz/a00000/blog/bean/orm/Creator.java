package xyz.a00000.blog.bean.orm;

import lombok.Data;

import java.io.Serializable;

@Data
public class Creator implements Serializable {

    private String username;
    private String password;

}
