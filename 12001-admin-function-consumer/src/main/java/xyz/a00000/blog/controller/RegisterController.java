package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.service.RegisterService;

@RestController
@Slf4j
@RequestMapping("/api/admin/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @GetMapping("/generateRegisterId")
    public BaseActionResult<String> generateRegisterId() {
        log.info("准备生成注册id");
        BaseActionResult<String> result = registerService.generateRegisterId();
        log.info("生成完成, 准备返回.");
        return result;
    }

}
