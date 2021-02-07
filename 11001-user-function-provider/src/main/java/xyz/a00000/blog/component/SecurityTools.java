package xyz.a00000.blog.component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.a00000.blog.bean.orm.Authority;
import xyz.a00000.blog.bean.orm.Creator;
import xyz.a00000.blog.bean.orm.CreatorInfo;
import xyz.a00000.blog.bean.proxy.AuthorityBean;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.mapper.AuthorityMapper;
import xyz.a00000.blog.mapper.CreatorInfoMapper;
import xyz.a00000.blog.mapper.CreatorMapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@Transactional
public class SecurityTools {

    @Autowired
    private CreatorMapper creatorMapper;
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private CreatorInfoMapper creatorInfoMapper;

    public UserDetailsBean getCurrentUserDetails() {
        log.info("获取当前登录的用户.");
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            String username = authentication.getPrincipal().toString();
            log.info("获取认证用户的用户名: " + username);
            Collection<? extends GrantedAuthority> authorityNames = authentication.getAuthorities();
            log.info("获取认证用户的权限列表: " + Arrays.toString(authorityNames.toArray()));
            log.info("根据用户名, 查询用户信息.");
            QueryWrapper<Creator> qwCreator = new QueryWrapper<>();
            qwCreator.eq("username", username);
            Creator creator = creatorMapper.selectOne(qwCreator);
            log.info("根据用户id, 查询用户详细信息. 用户id: " + creator.getId());
            QueryWrapper<CreatorInfo> qwCreatorInfo = new QueryWrapper<>();
            qwCreatorInfo.eq("creator_id", creator.getId());
            CreatorInfo creatorInfo = creatorInfoMapper.selectOne(qwCreatorInfo);
            log.info("根据权限名称信息, 查询权限信息.");
            List<Authority> authorities = authorityMapper.selectByNames(authorityNames.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
            return new UserDetailsBean(creator, authorities.stream().map(AuthorityBean::new).collect(Collectors.toList()), creatorInfo);
        } catch (Exception ignore) {
            log.info("用户信息获取失败, 原因: " + ignore.getMessage());
        }
        return null;
    }

}
