package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.EssayComment;

public interface EssayCommentService {

    BaseActionResult<EssayComment> addComment(EssayComment essayComment);

    BaseActionResult<PageBean<EssayComment>> getEssayComments(PageForm<EssayComment> form);

}
