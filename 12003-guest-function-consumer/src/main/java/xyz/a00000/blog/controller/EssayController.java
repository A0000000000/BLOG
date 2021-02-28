package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.dto.EssayProxyBean;
import xyz.a00000.blog.bean.dto.EssayQueryBean;
import xyz.a00000.blog.bean.orm.EssayInfo;
import xyz.a00000.blog.service.EssayService;

@RestController
@Slf4j
@RequestMapping("/api/guest/essay")
public class EssayController {

    @Autowired
    private EssayService essayService;

    @PostMapping("/getEssayList")
    public BaseActionResult<PageBean<EssayQueryBean>> getEssayList(@RequestBody PageForm<EssayInfo> form) {
        log.info("根据分页条件, 查询分页数据.");
        BaseActionResult<PageBean<EssayQueryBean>> result = essayService.getEssayList(form);
        log.info("查询完成, 准备返回.");
        return result;
    }

    @GetMapping("/getEssayData")
    public BaseActionResult<EssayProxyBean> getEssayData(@RequestParam("essayId") Integer essayId, @RequestParam(value = "password", required = false) String password) {
        log.info("查询一篇随笔的相关信息.");
        BaseActionResult<EssayProxyBean> result = essayService.getEssayData(essayId, password);
        log.info("查询完成, 准备返回.");
        return result;
    }

}
