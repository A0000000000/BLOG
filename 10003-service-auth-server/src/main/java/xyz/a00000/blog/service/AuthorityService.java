package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.orm.Authority;
import xyz.a00000.blog.mapper.AuthorityMapper;

import java.io.Serializable;
import java.util.List;

public interface AuthorityService extends BaseService<Authority, AuthorityMapper> {

    List<Authority> selectUseCreatorId(Serializable creatorId);

}
