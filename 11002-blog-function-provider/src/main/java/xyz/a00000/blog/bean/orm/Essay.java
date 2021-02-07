package xyz.a00000.blog.bean.orm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("essay")
public class Essay implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String content;

}
