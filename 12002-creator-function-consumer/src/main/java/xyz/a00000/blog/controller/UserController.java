package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.dto.RegisterParams;
import xyz.a00000.blog.bean.dto.UserView;
import xyz.a00000.blog.bean.orm.Creator;
import xyz.a00000.blog.service.UserService;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/creator/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public BaseActionResult<Map<String, Object>> login(@RequestBody Creator creator) {
        log.info("用户登录.");
        BaseActionResult<Map<String, Object>> result = userService.login(creator);
        log.info("登录完成, 准备返回密钥.");
        return result;
    }

    @PostMapping("/register")
    public BaseActionResult<UserView> register(@RequestBody RegisterParams params) {
        log.info("用户注册.");
        BaseActionResult<UserView> result = userService.register(params);
        log.info("注册完成, 准备返回数据.");
        return result;
    }

    @PutMapping("/updateUserInfo")
    public BaseActionResult<UserView> updateUserInfo(@RequestBody RegisterParams params) {
        log.info("用户信息更新.");
        BaseActionResult<UserView> result = userService.updateUserInfo(params);
        log.info("用户信息更新完成.");
        return result;
    }

}
