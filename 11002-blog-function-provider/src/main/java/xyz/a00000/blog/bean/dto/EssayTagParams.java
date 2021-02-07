package xyz.a00000.blog.bean.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EssayTagParams implements Serializable {

    private Integer essayId;
    private String tagName;

}
