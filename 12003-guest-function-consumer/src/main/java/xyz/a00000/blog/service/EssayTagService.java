package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.EssayTag;

import java.util.List;

public interface EssayTagService {

    BaseActionResult<List<EssayTag>> getEssayTags(Integer essayId);

}
