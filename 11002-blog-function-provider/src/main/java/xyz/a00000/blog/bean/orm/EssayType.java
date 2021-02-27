package xyz.a00000.blog.bean.orm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("essay_type")
public class EssayType implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("creator_id")
    private Integer creatorId;
    @TableField("type_name")
    private String typeName;
    private String message;
    @TableField("create_time")
    private Date createTime;

}
