package xyz.a00000.blog.bean.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EssayQueryBean implements Serializable {

    private Integer id;
    private String title;
    private Date createTime;
    private Boolean password;
    private Integer star;
    private String creator;

}
