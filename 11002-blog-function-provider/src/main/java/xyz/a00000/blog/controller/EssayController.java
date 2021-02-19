package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.dto.EssayInitResultBean;
import xyz.a00000.blog.bean.dto.EssayInitParamsBean;
import xyz.a00000.blog.bean.orm.Essay;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.component.ResultCodeTools;
import xyz.a00000.blog.component.SecurityTools;
import xyz.a00000.blog.service.EssayService;

import javax.servlet.http.HttpServletRequest;

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

    @PostMapping("/updateEssay")
    public BaseActionResult<String> updateEssay(@RequestBody EssayInitParamsBean params) {
        log.info("更新一篇随笔.");
        log.info("加载用户信息.");
        UserDetailsBean currentUserDetails = securityTools.getCurrentUserDetails();
        log.info("更新随笔.");
        BaseServiceResult<String> result = essayService.updateEssay(params, currentUserDetails);
        log.info("更新完成, 返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PostMapping("/deleteEssay")
    public BaseActionResult<Void> deleteEssayById(@RequestBody Essay essay) {
        log.info("删除一篇随笔.");
        log.info("加载用户信息.");
        UserDetailsBean currentUserDetails = securityTools.getCurrentUserDetails();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        log.info("获取请求对象.");
        HttpServletRequest request = attributes.getRequest();
        log.info("获取请求授权密钥.");
        String authorization = request.getHeader("Authorization");
        log.info("授权密钥: " + authorization);
        log.info("删除随笔.");
        BaseServiceResult<Void> result = essayService.deleteEssay(essay, authorization, currentUserDetails);
        log.info("删除成功, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

}
