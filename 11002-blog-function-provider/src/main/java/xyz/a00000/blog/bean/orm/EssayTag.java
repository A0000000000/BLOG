package xyz.a00000.blog.bean.orm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("essay_tag")
public class EssayTag implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("creator_id")
    private Integer creatorId;
    @TableField("tag_name")
    private String tagName;
    @TableField("create_time")
    private Date createTime;

}
