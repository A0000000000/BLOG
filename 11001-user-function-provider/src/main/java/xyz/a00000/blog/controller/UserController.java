package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.dto.RegisterParams;
import xyz.a00000.blog.bean.dto.UserView;
import xyz.a00000.blog.component.ResultCodeTools;
import xyz.a00000.blog.service.CreatorService;


@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private CreatorService creatorService;
    @Autowired
    private ResultCodeTools resultCodeTools;

    @PostMapping("/register")
    public BaseActionResult<UserView> register(@RequestBody RegisterParams params) {
        log.info("执行用户注册方法.");
        BaseServiceResult<UserView> result = creatorService.register(params);
        log.info("注册完成, 结果: " + (result != null ? result.getCode() : null));
        return BaseActionResult.from(result, resultCodeTools);
    }

}
