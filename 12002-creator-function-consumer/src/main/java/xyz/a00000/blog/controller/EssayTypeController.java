package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.EssayType;
import xyz.a00000.blog.service.EssayTypeService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/creator/essayType")
public class EssayTypeController {

    @Autowired
    private EssayTypeService essayTypeService;

    @GetMapping("/getAllEssayType")
    public BaseActionResult<List<EssayType>> getAllEssayType() {
        log.info("获取全部随笔类型.");
        BaseActionResult<List<EssayType>> result = essayTypeService.getAllEssayType();
        log.info("获取完成, 准备返回.");
        return result;
    }

    @PostMapping("/getEssayTypeByForm")
    public BaseActionResult<PageBean<EssayType>> getEssayTypeByForm(@RequestBody PageForm<EssayType> form) {
        log.info("分页查询随笔类型.");
        BaseActionResult<PageBean<EssayType>> result = essayTypeService.getEssayTypeByForm(form);
        log.info("查询完成, 准备返回.");
        return result;
    }

    @PostMapping("/createEssayType")
    public BaseActionResult<EssayType> createEssayType(@RequestBody EssayType type) {
        log.info("创建新的随笔类型.");
        BaseActionResult<EssayType> result = essayTypeService.createEssayType(type);
        log.info("创建完成, 准备返回.");
        return result;
    }

    @PutMapping("/updateEssayType")
    public BaseActionResult<EssayType> updateEssayType(@RequestBody EssayType type) {
        log.info("更新一条随笔记录.");
        BaseActionResult<EssayType> result = essayTypeService.updateEssayType(type);
        log.info("更新完成, 准备返回.");
        return result;
    }

    @DeleteMapping("/deleteEssayTypeById/{id}")
    public BaseActionResult<Void> deleteEssayTypeById(@PathVariable("id") Integer id) {
        log.info("删除一条随笔记录.");
        BaseActionResult<Void> result = essayTypeService.deleteEssayTypeById(id);
        log.info("删除完成, 准备返回.");
        return result;
    }

}
