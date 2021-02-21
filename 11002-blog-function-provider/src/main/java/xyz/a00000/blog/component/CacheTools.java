package xyz.a00000.blog.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.a00000.blog.bean.cache.EssayInitCache;
import xyz.a00000.blog.bean.orm.SystemConfig;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CacheTools {

    @Autowired
    private RedisTemplate<String, EssayInitCache> essayInitCacheRedisTemplate;
    @Autowired
    private SystemConfigTools systemConfigTools;

    public void setEssayInitCache(EssayInitCache essayInitCache) {
        log.info("向缓存中存入一条缓存记录.");
        ValueOperations<String, EssayInitCache> ops = essayInitCacheRedisTemplate.opsForValue();
        SystemConfig config = systemConfigTools.getSystemConfig("register_token_expire");
        ops.set(essayInitCache.getCacheId(), essayInitCache, Long.parseLong(config.getValue()), TimeUnit.SECONDS);
        log.info("缓存成功.");
    }

    public EssayInitCache getEssayInitCache(String cacheId) {
        log.info("检查key是否为空.");
        if (StringUtils.isEmpty(cacheId)) {
            return null;
        }
        log.info("从缓存中取一条记录.");
        if (!essayInitCacheRedisTemplate.hasKey(cacheId)) {
            log.info("缓存不存在此记录.");
            return null;
        }
        log.info("命中缓存, 取得缓存.");
        ValueOperations<String, EssayInitCache> ops = essayInitCacheRedisTemplate.opsForValue();
        EssayInitCache essayInitCache = ops.get(cacheId);
        log.info("从缓存中删除记录.");
        essayInitCacheRedisTemplate.delete(cacheId);
        log.info("返回缓存.");
        return essayInitCache;
    }

}
