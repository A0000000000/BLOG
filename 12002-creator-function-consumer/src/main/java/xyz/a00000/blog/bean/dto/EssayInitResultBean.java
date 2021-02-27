package xyz.a00000.blog.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EssayInitResultBean implements Serializable {

    private String cacheId;
    private Integer essayId;
    private Integer essayInfoId;

}
