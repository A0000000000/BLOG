package xyz.a00000.blog.bean.orm;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EssayInfo implements Serializable {

    private Integer id;
    private Integer essayId;
    private Integer essayTypeId;
    private Integer creatorId;
    private Date createTime;
    private String password;
    private Integer visitCount;
    private Integer star;

}
