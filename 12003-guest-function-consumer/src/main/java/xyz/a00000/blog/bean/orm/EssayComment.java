package xyz.a00000.blog.bean.orm;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EssayComment implements Serializable {

    private Integer id;
    private Integer essayId;
    private String nickname;
    private String message;
    private Date commentTime;

}
