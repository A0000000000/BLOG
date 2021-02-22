package xyz.a00000.blog.bean.orm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("access_info")
public class AccessInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String url;
    private String ip;
    @TableField("access_time")
    private Date accessTime;

}
