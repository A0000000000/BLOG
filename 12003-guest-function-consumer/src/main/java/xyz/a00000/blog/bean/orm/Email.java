package xyz.a00000.blog.bean.orm;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Email implements Serializable {

    private Integer id;
    private String receiver;
    private String subject;
    private String text;
    private Date sendTime;

}
