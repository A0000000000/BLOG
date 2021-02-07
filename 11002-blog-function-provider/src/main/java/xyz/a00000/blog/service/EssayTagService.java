package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.dto.EssayTagParams;
import xyz.a00000.blog.bean.orm.EssayEssayTag;
import xyz.a00000.blog.bean.orm.EssayTag;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.mapper.EssayTagMapper;

public interface EssayTagService extends BaseService<EssayTag, EssayTagMapper> {

    BaseServiceResult<EssayTag> addNewTag(EssayTagParams params, UserDetailsBean currentUserDetails);

    BaseServiceResult<Void> removeTag(EssayEssayTag essayEssayTag, UserDetailsBean currentUserDetails);

}
