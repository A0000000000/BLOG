package xyz.a00000.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.SystemConfig;
import xyz.a00000.blog.feign.SystemConfigFeign;
import xyz.a00000.blog.service.SystemConfigService;

@Slf4j
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    private SystemConfigFeign systemConfigFeign;

    @Override
    public BaseActionResult<SystemConfig> getSystemConfigById(Integer id) {
        log.info("向远程服务查询一条记录.");
        BaseActionResult<SystemConfig> result = systemConfigFeign.getSystemConfigById(id);
        log.info("访问完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<PageBean<SystemConfig>> getSystemConfigByForm(PageForm<SystemConfig> form) {
        log.info("准备向远程服务请求分页数据.");
        BaseActionResult<PageBean<SystemConfig>> result = systemConfigFeign.getSystemConfigByForm(form);
        log.info("访问完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<SystemConfig> createSystemConfig(SystemConfig systemConfig) {
        log.info("准备向远程服务发起请求, 新建一条记录.");
        BaseActionResult<SystemConfig> result = systemConfigFeign.createSystemConfig(systemConfig);
        log.info("访问完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<SystemConfig> updateSystemConfig(SystemConfig systemConfig) {
        log.info("准备向远程服务更新一条记录.");
        BaseActionResult<SystemConfig> result = systemConfigFeign.updateSystemConfig(systemConfig);
        log.info("访问完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<Void> deleteSystemConfigById(Integer id) {
        log.info("准备向远程服务删除一条记录.");
        BaseActionResult<Void> result = systemConfigFeign.deleteSystemConfigById(id);
        log.info("请求完成, 准备返回.");
        return result;
    }

}
