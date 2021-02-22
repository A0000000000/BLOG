package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.Email;
import xyz.a00000.blog.component.ResultCodeTools;
import xyz.a00000.blog.service.EmailService;

@RestController
@Slf4j
@RequestMapping("/api/website/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ResultCodeTools resultCodeTools;

    @GetMapping("/getEmailById/{id}")
    public BaseActionResult<Email> getEmailById(@PathVariable("id") Integer id) {
        log.info("通过id查询一封邮件的信息.");
        BaseServiceResult<Email> result = emailService.selectUseId(id);
        log.info("查询完成, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PostMapping("/getEmailByForm")
    public BaseActionResult<PageBean<Email>> getEmailByForm(@RequestBody PageForm<Email> form) {
        log.info("分页查询邮件信息.");
        BaseServiceResult<PageBean<Email>> result = emailService.getEmailByForm(form);
        log.info("查询完成, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

}
