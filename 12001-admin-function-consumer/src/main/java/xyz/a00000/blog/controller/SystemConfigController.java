package xyz.a00000.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.SystemConfig;
import xyz.a00000.blog.service.SystemConfigService;

@Slf4j
@RestController
@RequestMapping("/api/admin/systemConfig")
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @GetMapping("/getSystemConfigById/{id}")
    public BaseActionResult<SystemConfig> getSystemConfigById(@PathVariable("id") Integer id) {
        log.info("根据id, 加载一条信息.");
        BaseActionResult<SystemConfig> result = systemConfigService.getSystemConfigById(id);
        log.info("加载完成, 准备返回.");
        return result;
    }

    @PostMapping("/getSystemConfigByForm")
    public BaseActionResult<PageBean<SystemConfig>> getSystemConfigByForm(@RequestBody PageForm<SystemConfig> form) {
        log.info("根据分页信息, 查询分页数据.");
        BaseActionResult<PageBean<SystemConfig>> result = systemConfigService.getSystemConfigByForm(form);
        log.info("查询完成, 准备返回.");
        return result;
    }

    @PostMapping("/createSystemConfig")
    public BaseActionResult<SystemConfig> createSystemConfig(@RequestBody SystemConfig systemConfig) {
        log.info("创建一条新记录.");
        BaseActionResult<SystemConfig> result = systemConfigService.createSystemConfig(systemConfig);
        log.info("创建完成, 准备返回.");
        return result;
    }

    @PutMapping("/updateSystemConfig")
    public BaseActionResult<SystemConfig> updateSystemConfig(@RequestBody SystemConfig systemConfig) {
        log.info("更新一条记录.");
        BaseActionResult<SystemConfig> result = systemConfigService.updateSystemConfig(systemConfig);
        log.info("创建完成, 准备返回.");
        return result;
    }

    @DeleteMapping("/deleteSystemConfigById/{id}")
    public BaseActionResult<Void> deleteSystemConfigById(@PathVariable("id") Integer id) {
        log.info("删除一条记录.");
        BaseActionResult<Void> result = systemConfigService.deleteSystemConfigById(id);
        log.info("删除完成, 准备返回.");
        return result;
    }

}
