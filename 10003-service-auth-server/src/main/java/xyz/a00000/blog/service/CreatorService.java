package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.orm.Creator;
import xyz.a00000.blog.mapper.CreatorMapper;

public interface CreatorService extends BaseService<Creator, CreatorMapper> {

    Creator selectUseUsername(String username);

}
