package xyz.a00000.blog.bean.orm;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Image implements Serializable {

    private Integer id;
    private Integer essayId;
    private Integer creatorId;
    private String originalFilename;
    private String filename;
    private Date uploadTime;
    private String contentType;
    private String password;

}
