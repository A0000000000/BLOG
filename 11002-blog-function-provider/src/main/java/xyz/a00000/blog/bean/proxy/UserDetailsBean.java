package xyz.a00000.blog.bean.proxy;

import lombok.Data;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.a00000.blog.bean.orm.Creator;
import xyz.a00000.blog.bean.orm.CreatorInfo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
public class UserDetailsBean implements UserDetails, Serializable {

    @NonNull
    private Creator creator;
    @NonNull
    private List<AuthorityBean> authorityBeans;
    @NonNull
    private CreatorInfo creatorInfo;

    public UserDetailsBean(@NonNull Creator creator, @NonNull List<AuthorityBean> authorityBeans, @NonNull CreatorInfo creatorInfo) {
        this.creator = creator;
        this.authorityBeans = authorityBeans;
        this.creatorInfo = creatorInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorityBeans;
    }

    @Override
    public String getPassword() {
        return this.creator.getPassword();
    }

    @Override
    public String getUsername() {
        return this.creator.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.creatorInfo.getAccountNonExpired() == 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.creatorInfo.getAccountNonLocked() == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.creatorInfo.getCredentialsNonExpired() == 0;
    }

    @Override
    public boolean isEnabled() {
        return this.creatorInfo.getEnable() == 0;
    }

}
