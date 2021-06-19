package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.dto.RegisterParams;
import xyz.a00000.blog.bean.dto.UserView;
import xyz.a00000.blog.component.ResultCodeTools;
import xyz.a00000.blog.component.SecurityTools;
import xyz.a00000.blog.service.CreatorService;

@RestController
@RequestMapping("/api/user/creator")
@Slf4j
public class CreatorController {

    @Autowired
    private CreatorService creatorService;
    @Autowired
    private ResultCodeTools resultCodeTools;
    @Autowired
    private SecurityTools securityTools;

    @PutMapping("/updateUserInfo")
    public BaseActionResult<UserView> updateUserInfo(@RequestBody RegisterParams params) {
        log.info("进入更新用户信息方法.");
        BaseServiceResult<UserView> result = creatorService.updateCreatorInfo(params, securityTools.getCurrentUserDetails());
        log.info("更新完成, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @GetMapping("/getUserInfo")
    public BaseActionResult<UserView> getUserInfo() {
        log.info("获取登录用户信息.");
        BaseServiceResult<UserView> result = creatorService.getUserInfo(securityTools.getCurrentUserDetails());
        log.info("获取完成, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

}
