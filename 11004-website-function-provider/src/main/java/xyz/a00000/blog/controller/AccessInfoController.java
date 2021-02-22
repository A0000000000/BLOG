package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.AccessInfo;
import xyz.a00000.blog.component.ResultCodeTools;
import xyz.a00000.blog.service.AccessInfoService;

@RestController
@Slf4j
@RequestMapping("/api/website/accessInfo")
public class AccessInfoController {

    @Autowired
    private ResultCodeTools resultCodeTools;

    @Autowired
    private AccessInfoService accessInfoService;

    @GetMapping("/getAccessInfoById/{id}")
    public BaseActionResult<AccessInfo> getAccessInfoById(@PathVariable("id") Integer id) {
        log.info("根据id查询一条访问记录.");
        BaseServiceResult<AccessInfo> result = accessInfoService.selectUseId(id);
        log.info("查询完成, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PostMapping("/getAccessInfoByForm")
    public BaseActionResult<PageBean<AccessInfo>> getAccessInfoByForm(@RequestBody PageForm<AccessInfo> form) {
        log.info("根据分页信息, 查询分页数据.");
        BaseServiceResult<PageBean<AccessInfo>> result = accessInfoService.getAccessInfoByForm(form);
        log.info("查询完成, 准备返回数据.");
        return BaseActionResult.from(result, resultCodeTools);
    }

}
