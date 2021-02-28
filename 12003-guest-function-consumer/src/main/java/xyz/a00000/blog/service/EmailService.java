package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.Email;

public interface EmailService {

    BaseActionResult<Email> sendEmail(Email email);

}
