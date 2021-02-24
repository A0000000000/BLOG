package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.SystemConfig;

public interface SystemConfigService {

    BaseActionResult<SystemConfig> getSystemConfigById(Integer id);

    BaseActionResult<PageBean<SystemConfig>> getSystemConfigByForm(PageForm<SystemConfig> form);

    BaseActionResult<SystemConfig> createSystemConfig(SystemConfig systemConfig);

    BaseActionResult<SystemConfig> updateSystemConfig(SystemConfig systemConfig);

    BaseActionResult<Void> deleteSystemConfigById(Integer id);

}
