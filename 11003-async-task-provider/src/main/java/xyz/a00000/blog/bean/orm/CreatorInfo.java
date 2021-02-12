package xyz.a00000.blog.bean.orm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@TableName("creator_info")
public class CreatorInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("creator_id")
    private Integer creatorId;
    @TableField("create_time")
    private Date createTime;
    private String email;
    private String message;
    @TableField("file_bucket")
    private String fileBucket;
    private Date birthday;
    @TableField("account_non_expired")
    private Integer accountNonExpired;
    @TableField("account_non_locked")
    private Integer accountNonLocked;
    @TableField("credentials_non_expired")
    private Integer credentialsNonExpired;
    private Integer enable;

}
