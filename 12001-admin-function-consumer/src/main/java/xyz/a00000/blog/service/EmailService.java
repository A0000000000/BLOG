package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.Email;

public interface EmailService {

    BaseActionResult<Email> getEmailById(Integer id);

    BaseActionResult<PageBean<Email>> getEmailByForm(PageForm<Email> form);

}
