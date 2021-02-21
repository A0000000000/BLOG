package xyz.a00000.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.SystemConfig;
import xyz.a00000.blog.mapper.SystemConfigMapper;
import xyz.a00000.blog.service.SystemConfigService;

import java.util.List;

@Service
@Slf4j
@Transactional
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfig, SystemConfigMapper> implements SystemConfigService {

    @Override
    @HystrixCommand(fallbackMethod = "getSystemConfigByForm_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<PageBean<SystemConfig>> getSystemConfigByForm(PageForm<SystemConfig> form) {
        log.info("准备查询分页数据.");
        log.info("构建返回数据对象, 并检查校验参数.");
        Integer sum = u.selectCount(null);
        PageBean<SystemConfig> bean = PageBean.createPageBean(form, sum);
        log.info("准备查询数据库.");
        List<SystemConfig> data = u.selectByForm(form);
        log.info("将数据设置回Bean.");
        bean.setData(data);
        log.info("返回数据.");
        return BaseServiceResult.getSuccessBean(bean);
    }

    public BaseServiceResult<PageBean<SystemConfig>> getSystemConfigByForm_fallback(PageForm<SystemConfig> form) {
        log.info("getSystemConfigByForm发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

    @Override
    @HystrixCommand(fallbackMethod = "createSystemConfig_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<SystemConfig> createSystemConfig(SystemConfig systemConfig) {
        log.info("校验参数是否合法.");
        if (systemConfig == null || StringUtils.isEmpty(systemConfig.getName()) || StringUtils.isEmpty(systemConfig.getValue())) {
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        log.info("判断name是否已存在.");
        QueryWrapper<SystemConfig> qwSystemConfig = new QueryWrapper<>();
        qwSystemConfig.eq("name", systemConfig.getName());
        SystemConfig one = u.selectOne(qwSystemConfig);
        if (one != null) {
            return BaseServiceResult.getFailedBean(new Exception("NAME_HAS_EXIST"), 12);
        }
        log.info("向数据库插入一条记录.");
        u.insert(systemConfig);
        log.info("返回结果.");
        return BaseServiceResult.getSuccessBean(systemConfig);
    }

    public BaseServiceResult<SystemConfig> createSystemConfig_fallback(SystemConfig systemConfig) {
        log.info("createSystemConfig发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

    @Override
    @HystrixCommand(fallbackMethod = "updateSystemConfig_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<SystemConfig> updateSystemConfig(SystemConfig systemConfig) {
        log.info("更新一条记录.");
        log.info("判断参数是否合法.");
        if (systemConfig == null || systemConfig.getId() == null) {
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        log.info("加载当前数据, 并设置为新数据.");
        SystemConfig current = u.selectById(systemConfig.getId());
        if (current == null) {
            return BaseServiceResult.getFailedBean(new Exception("NO_RECORD"), 9);
        }
        if (StringUtils.isEmpty(systemConfig.getName())) {
            current.setName(systemConfig.getName());
        }
        if (StringUtils.isEmpty(systemConfig.getValue())) {
            current.setValue(systemConfig.getValue());
        }
        if (StringUtils.isEmpty(systemConfig.getInfo())) {
            current.setInfo(systemConfig.getInfo());
        }
        log.info("准备更新数据.");
        u.updateById(current);
        log.info("更新完成, 准备返回.");
        return BaseServiceResult.getSuccessBean(current);
    }

    public BaseServiceResult<SystemConfig> updateSystemConfig_fallback(SystemConfig systemConfig) {
        log.info("updateSystemConfig发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

}
