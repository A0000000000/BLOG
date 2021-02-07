package xyz.a00000.blog.bean.proxy;

import lombok.Data;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import xyz.a00000.blog.bean.orm.Authority;

import java.io.Serializable;

@Data
public class AuthorityBean implements GrantedAuthority, Serializable {

    @NonNull
    private Authority authority;

    public AuthorityBean(@NonNull Authority authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority.getAuthorityName();
    }
}
