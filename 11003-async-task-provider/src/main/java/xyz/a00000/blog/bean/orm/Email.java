package xyz.a00000.blog.bean.orm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("email")
public class Email implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String receiver;
    private String subject;
    private String text;
    @TableField("send_time")
    private Date sendTime;

}
