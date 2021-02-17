package xyz.a00000.blog.bean.orm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("image")
public class Image implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("essay_id")
    private Integer essayId;
    @TableField("creator_id")
    private Integer creatorId;
    @TableField("original_filename")
    private String originalFilename;
    private String filename;
    @TableField("upload_time")
    private Date uploadTime;
    @TableField("content_type")
    private String contentType;
    private String password;

}
