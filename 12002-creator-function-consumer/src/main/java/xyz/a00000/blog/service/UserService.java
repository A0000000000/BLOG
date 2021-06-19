package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.dto.RegisterParams;
import xyz.a00000.blog.bean.dto.UserView;
import xyz.a00000.blog.bean.orm.Creator;

import java.util.Map;


public interface UserService {

    BaseActionResult<Map<String, Object>> login(Creator creator);

    BaseActionResult<UserView> register(RegisterParams params);

    BaseActionResult<UserView> updateUserInfo(RegisterParams params);

    BaseActionResult<Map<String, Object>> refresh(Map<String, String> params);

}
