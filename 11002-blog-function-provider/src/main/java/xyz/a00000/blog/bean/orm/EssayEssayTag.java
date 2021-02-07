package xyz.a00000.blog.bean.orm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("essay_essay_tag")
public class EssayEssayTag implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("essay_id")
    private Integer essayId;
    @TableField("essay_tag_id")
    private Integer essayTagId;
    @TableField("create_time")
    private Date createTime;

}
