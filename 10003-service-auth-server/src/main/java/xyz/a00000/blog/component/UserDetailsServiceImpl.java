package xyz.a00000.blog.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.a00000.blog.bean.orm.Authority;
import xyz.a00000.blog.bean.orm.Creator;
import xyz.a00000.blog.bean.orm.CreatorInfo;
import xyz.a00000.blog.bean.proxy.AuthorityBean;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.service.AuthorityService;
import xyz.a00000.blog.service.CreatorInfoService;
import xyz.a00000.blog.service.CreatorService;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsServiceImpl")
@Slf4j
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CreatorService creatorService;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private CreatorInfoService creatorInfoService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("根据用户名加载用户信息, username: " + s);
        Creator creator = creatorService.selectUseUsername(s);
        if (creator == null) {
            log.info("未能查询到该用户, username: " + s);
            return null;
        }
        List<Authority> authorities = authorityService.selectUseCreatorId(creator.getId());
        List<AuthorityBean> authorityBeans = authorities.stream().map(AuthorityBean::new).collect(Collectors.toList());
        CreatorInfo creatorInfo = creatorInfoService.selectUseCreatorId(creator.getId());
        log.info("查询成功, 准备返回用户信息, username: " + s);
        return new UserDetailsBean(creator, authorityBeans, creatorInfo);
    }

}
