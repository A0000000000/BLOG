package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.Email;
import xyz.a00000.blog.mapper.EmailMapper;

public interface EmailService extends BaseService<Email, EmailMapper>{

    BaseServiceResult<PageBean<Email>> getEmailByForm(PageForm<Email> form);

}
