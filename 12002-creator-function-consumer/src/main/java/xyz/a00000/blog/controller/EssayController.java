package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.dto.EssayInitParamsBean;
import xyz.a00000.blog.bean.dto.EssayInitResultBean;
import xyz.a00000.blog.service.EssayService;

@RestController
@Slf4j
@RequestMapping("/api/creator/essay")
public class EssayController {


    @Autowired
    private EssayService essayService;

    @PostMapping("/createEssay")
    public BaseActionResult<EssayInitResultBean> createEssay(@RequestBody EssayInitParamsBean params) {
        log.info("创建一条随笔记录.");
        BaseActionResult<EssayInitResultBean> result = essayService.createEssay(params);
        log.info("创建完成, 准备返回.");
        return result;
    }

    @PutMapping("/updateEssay")
    public BaseActionResult<String> updateEssay(@RequestBody EssayInitParamsBean params) {
        log.info("更新一条随笔记录.");
        BaseActionResult<String> result = essayService.updateEssay(params);
        log.info("更新完成, 准备返回.");
        return result;
    }

    @DeleteMapping("/deleteEssayById/{id}")
    public BaseActionResult<Void> deleteEssayById(@PathVariable("id") Integer id) {
        log.info("删除一条随笔记录.");
        BaseActionResult<Void> result = essayService.deleteEssayById(id);
        log.info("删除完成, 准备返回.");
        return result;
    }

}
