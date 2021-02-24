package xyz.a00000.blog.bean.orm;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AccessInfo implements Serializable {

    private Integer id;
    private String url;
    private String ip;
    private Date accessTime;

}
