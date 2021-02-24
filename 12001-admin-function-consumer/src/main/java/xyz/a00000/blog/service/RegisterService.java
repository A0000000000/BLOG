package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseActionResult;

public interface RegisterService {

    BaseActionResult<String> generateRegisterId();

}
