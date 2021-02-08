package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.orm.EssayComment;
import xyz.a00000.blog.mapper.EssayCommentMapper;

public interface EssayCommentService extends BaseService<EssayComment, EssayCommentMapper> {

    BaseServiceResult<EssayComment> addComment(EssayComment essayComment);

}
