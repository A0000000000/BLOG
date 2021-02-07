package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.dto.RegisterParams;
import xyz.a00000.blog.bean.dto.UserView;
import xyz.a00000.blog.bean.orm.Creator;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.mapper.CreatorMapper;

public interface CreatorService extends BaseService<Creator, CreatorMapper> {

    BaseServiceResult<UserView> register(RegisterParams params);

    BaseServiceResult<UserView> updateCreatorInfo(RegisterParams params, UserDetailsBean currentUserDetails);

}
