package xyz.a00000.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.Email;
import xyz.a00000.blog.feign.EmailFeign;
import xyz.a00000.blog.service.EmailService;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailFeign emailFeign;

    @Override
    public BaseActionResult<Email> getEmailById(Integer id) {
        log.info("向远程服务器查询一条记录.");
        BaseActionResult<Email> result = emailFeign.getEmailById(id);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<PageBean<Email>> getEmailByForm(PageForm<Email> form) {
        log.info("向远程服务器查询分页数据.");
        BaseActionResult<PageBean<Email>> result = emailFeign.getEmailByForm(form);
        log.info("请求完成, 准备返回.");
        return result;
    }

}
