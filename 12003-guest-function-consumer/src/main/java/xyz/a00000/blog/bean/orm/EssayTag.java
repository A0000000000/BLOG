package xyz.a00000.blog.bean.orm;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EssayTag implements Serializable {

    private Integer id;
    private Integer creatorId;
    private String tagName;
    private Date createTime;

}
