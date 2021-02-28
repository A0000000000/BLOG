package xyz.a00000.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.Email;
import xyz.a00000.blog.feign.EmailFeign;
import xyz.a00000.blog.service.EmailService;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailFeign emailFeign;

    @Override
    public BaseActionResult<Email> sendEmail(Email email) {
        log.info("准备请求远程服务, 发送一封邮件.");
        BaseActionResult<Email> result = emailFeign.sendEmail(email);
        log.info("请求完成, 准备返回.");
        return result;
    }

}
