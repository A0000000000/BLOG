package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.Email;
import xyz.a00000.blog.service.EmailService;

@Slf4j
@RestController
@RequestMapping("/api/admin/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/getEmailById/{id}")
    public BaseActionResult<Email> getEmailById(@PathVariable("id") Integer id) {
        log.info("通过id获取邮件信息.");
        BaseActionResult<Email> result = emailService.getEmailById(id);
        log.info("查询完成, 准备返回.");
        return result;
    }

    @PostMapping("/getEmailByForm")
    public BaseActionResult<PageBean<Email>> getEmailByForm(@RequestBody PageForm<Email> form) {
        log.info("根据分页信息, 查询分页数据.");
        BaseActionResult<PageBean<Email>> result = emailService.getEmailByForm(form);
        log.info("查询完成, 准备返回.");
        return result;
    }


}
