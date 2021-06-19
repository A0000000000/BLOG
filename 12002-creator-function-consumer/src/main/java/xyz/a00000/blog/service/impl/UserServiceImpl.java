package xyz.a00000.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.dto.RegisterParams;
import xyz.a00000.blog.bean.dto.UserView;
import xyz.a00000.blog.bean.orm.Creator;
import xyz.a00000.blog.feign.CreatorFeign;
import xyz.a00000.blog.feign.OAuthFeign;
import xyz.a00000.blog.feign.RegisterFeign;
import xyz.a00000.blog.service.UserService;

import java.util.Map;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private OAuthFeign oAuthFeign;
    @Autowired
    private RegisterFeign registerFeign;
    @Autowired
    private CreatorFeign creatorFeign;

    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;
    @Value("${security.oauth2.client.grant-type.login}")
    private String loginGrantType;
    @Value("${security.oauth2.client.grant-type.refresh}")
    private String refreshGrantType;

    @Override
    public BaseActionResult<Map<String, Object>> login(Creator creator) {
        log.info("准备请求远程服务器登录.");
        BaseActionResult<Map<String, Object>> result = oAuthFeign.login(clientId, clientSecret, loginGrantType, creator.getUsername(), creator.getPassword());
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<UserView> register(RegisterParams params) {
        log.info("准备请求远程服务进行注册.");
        BaseActionResult<UserView> result = registerFeign.register(params);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<UserView> updateUserInfo(RegisterParams params) {
        log.info("准备请求远程服务进行注册.");
        BaseActionResult<UserView> result = creatorFeign.updateUserInfo(params);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<Map<String, Object>> refresh(Map<String, String> params) {
        log.info("准备请求远程服务进行刷新token");
        String token = params.get("token");
        BaseActionResult<Map<String, Object>> result = oAuthFeign.refresh(clientId, clientSecret, refreshGrantType, token);
        log.info("刷新完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<UserView> getUserInfo() {
        log.info("请求远程服务, 获取登录用户的信息");
        BaseActionResult<UserView> result = creatorFeign.getUserInfo();
        log.info("获取完成, 准备返回.");
        return result;
    }

}
