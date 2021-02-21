package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.component.ResultCodeTools;
import xyz.a00000.blog.service.CodeContrastService;

@RestController
@Slf4j
@RequestMapping("/api/website/codeContrast")
public class CodeContrastController {

    @Autowired
    private CodeContrastService codeContrastService;

    @Autowired
    private ResultCodeTools resultCodeTools;

    @GetMapping("/getCodeContrastById/{id}")
    public BaseActionResult<CodeContrast> getCodeContrastById(@PathVariable("id") Integer id) {
        log.info("获取一条记录.");
        BaseServiceResult<CodeContrast> result = codeContrastService.selectUseId(id);
        log.info("准备返回数据.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PostMapping("/getCodeContrastByForm")
    public BaseActionResult<PageBean<CodeContrast>> getCodeContrastByForm(@RequestBody PageForm<CodeContrast> form) {
        log.info("分页查询数据.");
        BaseServiceResult<PageBean<CodeContrast>> result = codeContrastService.getCodeContrastByForm(form);
        log.info("查询完成, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PostMapping("createCodeContrast")
    public BaseActionResult<CodeContrast> createCodeContrast(@RequestBody CodeContrast codeContrast) {
        log.info("添加一条记录.");
        BaseServiceResult<CodeContrast> result = codeContrastService.createCodeContrast(codeContrast);
        log.info("准备返回数据.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PutMapping("/updateCodeContrast")
    public BaseActionResult<CodeContrast> updateCodeContrast(@RequestBody CodeContrast codeContrast) {
        log.info("准备更新一条数据.");
        BaseServiceResult<CodeContrast> result = codeContrastService.updateCodeContrast(codeContrast);
        log.info("更新完成, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @DeleteMapping("/deleteCodeContrast/{id}")
    public BaseActionResult<Void> deleteCodeContrast(@PathVariable("id") Integer id) {
        log.info("删除一条记录.");
        codeContrastService.delete(id);
        log.info("删除完成, 准备返回.");
        return BaseActionResult.from(BaseServiceResult.getSuccessBean(null), resultCodeTools);
    }

}
