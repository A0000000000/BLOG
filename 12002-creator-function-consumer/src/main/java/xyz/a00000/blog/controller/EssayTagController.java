package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.dto.EssayTagParams;
import xyz.a00000.blog.bean.orm.EssayTag;
import xyz.a00000.blog.service.EssayTagService;

@RestController
@Slf4j
@RequestMapping("/api/creator/essayTag")
public class EssayTagController {

    @Autowired
    private EssayTagService essayTagService;

    @PostMapping("/addNewTag")
    public BaseActionResult<EssayTag> addNewTag(@RequestBody EssayTagParams params) {
        log.info("增加一条标签记录.");
        BaseActionResult<EssayTag> result = essayTagService.addNewTag(params);
        log.info("增加完成, 准备返回.");
        return result;
    }

    @DeleteMapping("/removeTag/{id}")
    public BaseActionResult<Void> removeTag(@PathVariable("id") Integer id) {
        log.info("删除一条标签记录.");
        BaseActionResult<Void> result = essayTagService.removeTag(id);
        log.info("删除完成, 准备返回.");
        return result;
    }

}
