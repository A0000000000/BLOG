package xyz.a00000.blog.bean.orm;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Creator implements Serializable {

    private Integer id;
    private String username;
    private String password;

}
