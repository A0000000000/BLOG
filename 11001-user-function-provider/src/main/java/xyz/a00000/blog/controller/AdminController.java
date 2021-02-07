package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.component.ResultCodeTools;
import xyz.a00000.blog.service.AdminService;

@RestController
@RequestMapping("/api/user/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ResultCodeTools resultCodeTools;

    @GetMapping("/generateRegisterId")
    public BaseActionResult<String> generateRegisterId() {
        log.info("执行获取注册id的控制器方法.");
        BaseServiceResult<String> source = adminService.generateRegisterId("ROLE_CREATOR");
        log.info("生成完成. 结果: " + (source != null ? source.getCode() : null));
        return BaseActionResult.from(source, resultCodeTools);
    }

}
