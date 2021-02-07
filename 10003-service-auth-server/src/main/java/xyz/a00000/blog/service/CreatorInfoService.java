package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.orm.CreatorInfo;
import xyz.a00000.blog.mapper.CreatorInfoMapper;

public interface CreatorInfoService extends BaseService<CreatorInfo, CreatorInfoMapper> {

    CreatorInfo selectUseCreatorId(Integer creatorId);

}
