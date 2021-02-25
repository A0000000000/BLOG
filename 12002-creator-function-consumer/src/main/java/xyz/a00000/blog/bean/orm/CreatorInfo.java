package xyz.a00000.blog.bean.orm;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import xyz.a00000.blog.bean.dto.RegisterParams;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CreatorInfo implements Serializable {

    private Integer id;
    private Integer creatorId;
    private Date createTime;
    private String email;
    private String message;
    private String fileBucket;
    private Date birthday;
    private Integer accountNonExpired;
    private Integer accountNonLocked;
    private Integer credentialsNonExpired;
    private Integer enable;

    public CreatorInfo(@NonNull RegisterParams params) {
        this.createTime = new Date();
        this.email = params.getEmail();
        this.message = params.getMessage();
        this.fileBucket = UUID.randomUUID().toString().replaceAll("-", "");
        this.birthday = params.getBirthday();
        this.accountNonExpired = 0;
        this.accountNonLocked = 0;
        this.credentialsNonExpired = 0;
        this.enable = 0;
    }
}
