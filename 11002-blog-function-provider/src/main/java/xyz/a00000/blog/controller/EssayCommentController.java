package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.orm.EssayComment;
import xyz.a00000.blog.component.ResultCodeTools;
import xyz.a00000.blog.service.EssayCommentService;

@RestController
@Slf4j
@RequestMapping("/api/blog/essayComment")
public class EssayCommentController {

    @Autowired
    private ResultCodeTools resultCodeTools;

    @Autowired
    private EssayCommentService essayCommentService;

    @PostMapping("/addComment")
    public BaseActionResult<EssayComment> addComment(@RequestBody EssayComment essayComment) {
        log.info("添加一条评论.");
        BaseServiceResult<EssayComment> result = essayCommentService.addComment(essayComment);
        log.info("添加完成.");
        return BaseActionResult.from(result, resultCodeTools);
    }

}
