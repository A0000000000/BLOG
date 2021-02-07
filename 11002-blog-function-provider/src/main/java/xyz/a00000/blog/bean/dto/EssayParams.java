package xyz.a00000.blog.bean.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EssayParams implements Serializable {

    private String title;
    private String type;
    private String password;
    private String content;
    private EssayInitResultBean data;

}
