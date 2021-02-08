package xyz.a00000.blog.bean.orm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("essay_comment")
public class EssayComment implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("essay_id")
    private Integer essayId;
    private String nickname;
    private String message;
    @TableField("comment_time")
    private Date commentTime;

}
