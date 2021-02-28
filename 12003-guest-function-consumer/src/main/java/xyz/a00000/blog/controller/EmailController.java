package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.Email;
import xyz.a00000.blog.service.EmailService;

@RestController
@Slf4j
@RequestMapping("/api/guest/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public BaseActionResult<Email> sendEmail(@RequestBody Email email) {
        log.info("准备发送一封邮件.");
        BaseActionResult<Email> result = emailService.sendEmail(email);
        log.info("发送完成, 准备返回.");
        return result;
    }

}
