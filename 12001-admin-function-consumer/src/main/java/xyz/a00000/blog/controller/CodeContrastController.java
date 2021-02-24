package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.service.CodeContrastService;

@Slf4j
@RestController
@RequestMapping("/api/admin/codeContrast")
public class CodeContrastController {

    @Autowired
    private CodeContrastService codeContrastService;

    @GetMapping("/getCodeContrastById/{id}")
    public BaseActionResult<CodeContrast> getCodeContrastById(@PathVariable("id") Integer id) {
        log.info("根据id查询一条记录.");
        BaseActionResult<CodeContrast> result = codeContrastService.getCodeContrastById(id);
        log.info("查询完成, 准备返回.");
        return result;
    }

    @PostMapping("/getCodeContrastByForm")
    public BaseActionResult<PageBean<CodeContrast>> getCodeContrastByForm(@RequestBody PageForm<CodeContrast> form) {
        log.info("根据分页信息查询分页数据.");
        BaseActionResult<PageBean<CodeContrast>> result = codeContrastService.getCodeContrastByForm(form);
        log.info("查询完成, 准备返回.");
        return result;
    }

    @PostMapping("createCodeContrast")
    public BaseActionResult<CodeContrast> createCodeContrast(@RequestBody CodeContrast codeContrast) {
        log.info("创建一条新纪录.");
        BaseActionResult<CodeContrast> result = codeContrastService.createCodeContrast(codeContrast);
        log.info("创建完成, 准备返回.");
        return result;
    }

    @PutMapping("/updateCodeContrast")
    public BaseActionResult<CodeContrast> updateCodeContrast(@RequestBody CodeContrast codeContrast) {
        log.info("更新一条记录.");
        BaseActionResult<CodeContrast> result = codeContrastService.updateCodeContrast(codeContrast);
        log.info("更新完成, 准备返回.");
        return result;
    }

    @DeleteMapping("/deleteCodeContrast/{id}")
    public BaseActionResult<Void> deleteCodeContrast(@PathVariable("id") Integer id) {
        log.info("删除一条记录.");
        BaseActionResult<Void> result = codeContrastService.deleteCodeContrast(id);
        log.info("删除完成, 准备返回.");
        return result;
    }

}
