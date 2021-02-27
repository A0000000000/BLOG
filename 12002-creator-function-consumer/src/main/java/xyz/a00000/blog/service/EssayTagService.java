package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.dto.EssayTagParams;
import xyz.a00000.blog.bean.orm.EssayTag;

public interface EssayTagService {

    BaseActionResult<EssayTag> addNewTag(EssayTagParams params);

    BaseActionResult<Void> removeTag(Integer id);

}
