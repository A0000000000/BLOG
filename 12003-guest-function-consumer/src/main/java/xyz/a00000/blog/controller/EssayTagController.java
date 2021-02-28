package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.EssayTag;
import xyz.a00000.blog.service.EssayTagService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/guest/essayTag")
public class EssayTagController {

    @Autowired
    private EssayTagService essayTagService;

    @GetMapping("/getEssayTags/{id}")
    public BaseActionResult<List<EssayTag>> getEssayTags(@PathVariable("id") Integer essayId) {
        log.info("加载一篇随笔的标签信息.");
        BaseActionResult<List<EssayTag>> result = essayTagService.getEssayTags(essayId);
        log.info("加载完成, 准备返回/");
        return result;
    }

}
