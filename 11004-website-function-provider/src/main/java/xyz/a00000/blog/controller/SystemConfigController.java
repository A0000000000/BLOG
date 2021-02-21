package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.SystemConfig;
import xyz.a00000.blog.component.ResultCodeTools;
import xyz.a00000.blog.service.SystemConfigService;

@RestController
@Slf4j
@RequestMapping("/api/website/systemConfig")
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private ResultCodeTools resultCodeTools;

    @GetMapping("/getSystemConfigById/{id}")
    public BaseActionResult<SystemConfig> getSystemConfigById(@PathVariable("id") Integer id) {
        log.info("根据id, 查询一项系统配置.");
        BaseServiceResult<SystemConfig> result = systemConfigService.selectUseId(id);
        log.info("查询完成, 准备返回数据.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PostMapping("/getSystemConfigByForm")
    public BaseActionResult<PageBean<SystemConfig>> getSystemConfigByForm(@RequestBody PageForm<SystemConfig> form) {
        log.info("根据分页信息, 查询分页数据.");
        BaseServiceResult<PageBean<SystemConfig>> result = systemConfigService.getSystemConfigByForm(form);
        log.info("查询完成, 准备返回.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PostMapping("/createSystemConfig")
    public BaseActionResult<SystemConfig> createSystemConfig(@RequestBody SystemConfig systemConfig) {
        log.info("创建一条新配置.");
        BaseServiceResult<SystemConfig> result = systemConfigService.createSystemConfig(systemConfig);
        log.info("创建完成, 准备返回数据.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @PutMapping("/updateSystemConfig")
    public BaseActionResult<SystemConfig> updateSystemConfig(@RequestBody SystemConfig systemConfig) {
        log.info("更新一条配置.");
        BaseServiceResult<SystemConfig> result = systemConfigService.updateSystemConfig(systemConfig);
        log.info("更新完成, 准备返回数据.");
        return BaseActionResult.from(result, resultCodeTools);
    }

    @DeleteMapping("/deleteSystemConfigById/{id}")
    public BaseActionResult<Void> deleteSystemConfigById(@PathVariable("id") Integer id) {
        log.info("准备删除一条记录.");
        systemConfigService.delete(id);
        log.info("删除完成, 准备返回.");
        return BaseActionResult.from(BaseServiceResult.getSuccessBean(null), resultCodeTools);
    }

}
