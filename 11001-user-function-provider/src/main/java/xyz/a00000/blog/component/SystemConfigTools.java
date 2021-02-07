package xyz.a00000.blog.component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.a00000.blog.bean.orm.SystemConfig;
import xyz.a00000.blog.mapper.SystemConfigMapper;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@Transactional
public class SystemConfigTools {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    private Map<String, SystemConfig> systemConfigMap;

    @PostConstruct
    public void init() {
        systemConfigMap = new ConcurrentHashMap<>();
    }

    public synchronized SystemConfig getSystemConfig(String name) {
        log.info("加载配置项: " + name);
        if (systemConfigMap.containsKey(name)) {
            log.info("缓存命中成功, name: " + name);
            return systemConfigMap.get(name);
        } else {
            log.info("向数据库发起查询, name: " + name);
            QueryWrapper<SystemConfig> qw = new QueryWrapper<>();
            qw.eq("name", name);
            SystemConfig systemConfig = systemConfigMapper.selectOne(qw);
            if (systemConfig != null) {
                log.info("查询成功, 返回并写入缓存, name: " + name);
                systemConfigMap.put(name, systemConfig);
                return systemConfig;
            } else {
                log.info("数据库没有该记录.");
                return null;
            }
        }
    }



}
