package xyz.a00000.blog.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.a00000.blog.bean.orm.Essay;
import xyz.a00000.blog.bean.orm.EssayInfo;
import xyz.a00000.blog.bean.orm.EssayType;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EssayProxyBean implements Serializable {

    private Essay essay;
    private EssayInfo essayInfo;
    private EssayType essayType;

}
