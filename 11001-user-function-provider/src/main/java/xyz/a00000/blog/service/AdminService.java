package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseServiceResult;

public interface AdminService {

    BaseServiceResult<String> generateRegisterId(String... authority);

}
