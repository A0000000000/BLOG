package xyz.a00000.blog.bean.orm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("essay_info")
public class EssayInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("essay_id")
    private Integer essayId;
    @TableField("essay_type_id")
    private Integer essayTypeId;
    @TableField("creator_id")
    private Integer creatorId;
    @TableField("create_time")
    private Date createTime;
    private String password;
    @TableField("visit_count")
    private Integer visitCount;
    private Integer star;

}
