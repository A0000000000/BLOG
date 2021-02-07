package xyz.a00000.blog.bean.orm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("creator_authority")
@AllArgsConstructor
public class CreatorAuthority implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("creator_id")
    private Integer creatorId;
    @TableField("authority_id")
    private Integer authorityId;

}
