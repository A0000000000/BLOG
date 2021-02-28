package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.EssayComment;
import xyz.a00000.blog.service.EssayCommentService;

@RestController
@Slf4j
@RequestMapping("/api/guest/essayComment")
public class EssayCommentController {

    @Autowired
    private EssayCommentService essayCommentService;

    @PostMapping("/addComment")
    public BaseActionResult<EssayComment> addComment(@RequestBody EssayComment essayComment) {
        log.info("向某篇随笔, 增加一条评论.");
        BaseActionResult<EssayComment> result = essayCommentService.addComment(essayComment);
        log.info("增加成功, 准备返回.");
        return result;
    }

    @PostMapping("/getEssayComments")
    public BaseActionResult<PageBean<EssayComment>> getEssayComments(@RequestBody PageForm<EssayComment> form) {
        log.info("分页查询评论信息.");
        BaseActionResult<PageBean<EssayComment>> result = essayCommentService.getEssayComments(form);
        log.info("查询完成, 准备返回.");
        return result;
    }

}
