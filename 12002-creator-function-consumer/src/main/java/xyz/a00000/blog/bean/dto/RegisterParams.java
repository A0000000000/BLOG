package xyz.a00000.blog.bean.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class RegisterParams implements Serializable {

    private String username;
    private String password;
    private String token;
    private String email;
    private String message;
    private Date birthday;

}
