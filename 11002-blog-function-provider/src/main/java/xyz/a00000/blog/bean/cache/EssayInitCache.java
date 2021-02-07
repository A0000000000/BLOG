package xyz.a00000.blog.bean.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import xyz.a00000.blog.bean.orm.Essay;
import xyz.a00000.blog.bean.orm.EssayInfo;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class EssayInitCache implements Serializable {
    
    private String cacheId;
    private Essay essay;
    private EssayInfo essayInfo;
    
}
