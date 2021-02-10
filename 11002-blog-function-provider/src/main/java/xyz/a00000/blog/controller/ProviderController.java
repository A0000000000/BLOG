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
import xyz.a00000.blog.bean.dto.EssayQueryBean;
import xyz.a00000.blog.bean.orm.EssayInfo;
import xyz.a00000.blog.component.ResultCodeTools;
import xyz.a00000.blog.service.ProviderService;

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


}
