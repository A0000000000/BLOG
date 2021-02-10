package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.dto.EssayProxyBean;
import xyz.a00000.blog.bean.dto.EssayQueryBean;
import xyz.a00000.blog.bean.orm.Essay;
import xyz.a00000.blog.bean.orm.EssayComment;
import xyz.a00000.blog.bean.orm.EssayInfo;
import xyz.a00000.blog.bean.orm.EssayTag;
import xyz.a00000.blog.component.ResultCodeTools;
import xyz.a00000.blog.service.ProviderService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/blog/provider")
public class ProviderController {

    @Autowired
    private ResultCodeTools resultCodeTools;

    @Autowired
    private ProviderService providerService;

    @PostMapping("/getEssayList")
    public BaseActionResult<PageBean<EssayQueryBean>> getEssayList(@RequestBody PageForm<EssayInfo> form) {
        log.info("查询随笔列表, 不包含正文.");
        BaseServiceResult<PageBean<EssayQueryBean>> result = providerService.getEssayList(form);
        log.info("查询完成, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PostMapping("/getEssayData")
    public BaseActionResult<EssayProxyBean> getEssayData(@RequestBody EssayInfo essayInfo) {
        log.info("查询一篇随笔的信息.");
        BaseServiceResult<EssayProxyBean> result = providerService.getEssayData(essayInfo);
        log.info("查询完成, 准备返回数据.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PostMapping("/getEssayTags")
    public BaseActionResult<List<EssayTag>> getEssayTags(@RequestBody Essay essay) {
        log.info("查询一篇随笔的标签.");
        BaseServiceResult<List<EssayTag>> result = providerService.getEssayTags(essay);
        log.info("查询完成, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PostMapping("/getEssayComments")
    public BaseActionResult<PageBean<EssayComment>> getEssayComments(@RequestBody PageForm<EssayComment> form) {
        log.info("加载随笔评论.");
        BaseServiceResult<PageBean<EssayComment>> result = providerService.getEssayComments(form);
        log.info("加载完成, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

}
