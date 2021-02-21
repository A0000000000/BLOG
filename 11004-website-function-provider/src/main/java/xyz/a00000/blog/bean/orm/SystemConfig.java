package xyz.a00000.blog.bean.orm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("system_config")
@Data
public class SystemConfig implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String value;
    private String info;

}
