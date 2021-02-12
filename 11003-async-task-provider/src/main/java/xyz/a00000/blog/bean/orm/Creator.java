package xyz.a00000.blog.bean.orm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@TableName("creator")
@NoArgsConstructor
public class Creator implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;

}
