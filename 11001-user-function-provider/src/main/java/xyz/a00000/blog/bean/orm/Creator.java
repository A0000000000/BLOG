package xyz.a00000.blog.bean.orm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import xyz.a00000.blog.bean.dto.RegisterParams;

import java.io.Serializable;

@Data
@TableName("creator")
@NoArgsConstructor
public class Creator implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;

    public Creator(@NonNull RegisterParams params) {
        this.username = params.getUsername();
        this.password = params.getPassword();
    }
}
