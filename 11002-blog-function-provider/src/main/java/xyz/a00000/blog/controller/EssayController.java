package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.dto.EssayInitParamsBean;
import xyz.a00000.blog.bean.dto.EssayInitResultBean;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.component.ResultCodeTools;
import xyz.a00000.blog.component.SecurityTools;
import xyz.a00000.blog.service.EssayService;

@RestController
@Slf4j
@RequestMapping("/api/blog/essay")
public class EssayController {

    @Autowired
    private SecurityTools securityTools;
    @Autowired
    private ResultCodeTools resultCodeTools;

    @Autowired
    private EssayService essayService;

    @PostMapping("/createEssay")
    public BaseActionResult<EssayInitResultBean> createEssay(@RequestBody EssayInitParamsBean params) {
        log.info("初始化一篇随笔.");
        log.info("加载用户信息.");
        UserDetailsBean currentUserDetails = securityTools.getCurrentUserDetails();
        log.info("初始化随笔.");
        BaseServiceResult<EssayInitResultBean> result = essayService.createEssay(params, currentUserDetails);
        log.info("初始化完成, 返回结果.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PutMapping("/updateEssay")
    public BaseActionResult<String> updateEssay(@RequestBody EssayInitParamsBean params) {
        log.info("更新一篇随笔.");
        log.info("加载用户信息.");
        UserDetailsBean currentUserDetails = securityTools.getCurrentUserDetails();
        log.info("更新随笔.");
        BaseServiceResult<String> result = essayService.updateEssay(params, currentUserDetails);
        log.info("更新完成, 返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @DeleteMapping("/deleteEssayById/{id}")
    public BaseActionResult<Void> deleteEssayById(@PathVariable("id") Integer id) {
        log.info("删除一篇随笔.");
        log.info("加载用户信息.");
        UserDetailsBean currentUserDetails = securityTools.getCurrentUserDetails();
        log.info("删除随笔.");
        BaseServiceResult<Void> result = essayService.deleteEssayById(id, currentUserDetails);
        log.info("删除成功, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

}
