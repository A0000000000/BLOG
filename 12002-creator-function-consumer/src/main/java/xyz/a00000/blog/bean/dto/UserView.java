package xyz.a00000.blog.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.a00000.blog.bean.orm.Authority;
import xyz.a00000.blog.bean.orm.Creator;
import xyz.a00000.blog.bean.orm.CreatorInfo;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserView implements Serializable {

    private Creator creator;
    private CreatorInfo creatorInfo;
    private List<Authority> authorities;

}
