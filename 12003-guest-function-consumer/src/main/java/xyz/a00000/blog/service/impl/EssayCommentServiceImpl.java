package xyz.a00000.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.EssayComment;
import xyz.a00000.blog.feign.BlogFeign;
import xyz.a00000.blog.feign.EssayCommentFeign;
import xyz.a00000.blog.service.EssayCommentService;

@Service
@Slf4j
public class EssayCommentServiceImpl implements EssayCommentService {

    @Autowired
    private BlogFeign blogFeign;
    @Autowired
    private EssayCommentFeign essayCommentFeign;

    @Override
    public BaseActionResult<EssayComment> addComment(EssayComment essayComment) {
        log.info("准备请求远程服务, 增加一条评论.");
        BaseActionResult<EssayComment> result = essayCommentFeign.addComment(essayComment);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<PageBean<EssayComment>> getEssayComments(PageForm<EssayComment> form) {
        log.info("根据分页信息, 准备向远程服务请求分页数据.");
        BaseActionResult<PageBean<EssayComment>> result = blogFeign.getEssayComments(form);
        log.info("请求完成, 准备返回.");
        return result;
    }

}
