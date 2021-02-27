package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.service.EssayCommentService;


@RestController
@Slf4j
@RequestMapping("/api/creator/essayComment")
public class EssayCommentController {


    @Autowired
    private EssayCommentService essayCommentService;

    @DeleteMapping("/removeComment/{id}")
    @PreAuthorize("hasRole('CREATOR')")
    public BaseActionResult<Void> removeComment(@PathVariable("id") Integer id) {
        log.info("删除一条评论.");
        BaseActionResult<Void> result = essayCommentService.removeComment(id);
        log.info("删除完成, 准备返回.");
        return result;
    }

}
