package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.SystemConfig;
import xyz.a00000.blog.mapper.SystemConfigMapper;

public interface SystemConfigService extends BaseService<SystemConfig, SystemConfigMapper> {

    BaseServiceResult<PageBean<SystemConfig>> getSystemConfigByForm(PageForm<SystemConfig> form);

    BaseServiceResult<SystemConfig> createSystemConfig(SystemConfig systemConfig);

    BaseServiceResult<SystemConfig> updateSystemConfig(SystemConfig systemConfig);

}
