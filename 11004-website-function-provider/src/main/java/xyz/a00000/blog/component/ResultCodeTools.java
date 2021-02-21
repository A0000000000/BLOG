package xyz.a00000.blog.component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.mapper.CodeContrastMapper;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@Transactional
public class ResultCodeTools {

    @Autowired
    private CodeContrastMapper codeContrastMapper;

    private Map<Integer, CodeContrast> codeContrastMap;

    @PostConstruct
    public void init() {
        codeContrastMap = new ConcurrentHashMap<>();
    }

    public synchronized CodeContrast getCodeContrast(Integer code) {
        if (codeContrastMap.containsKey(code)) {
            log.info("缓存命中, code: " + code);
            return codeContrastMap.get(code);
        } else {
            log.info("开始查询数据库, code: " + code);
            QueryWrapper<CodeContrast> qw = new QueryWrapper<>();
            qw.eq("code", code);
            CodeContrast codeContrast = codeContrastMapper.selectOne(qw);
            if (codeContrast != null) {
                log.info("查询成功, 写入缓存并返回.");
                codeContrastMap.put(code, codeContrast);
                return codeContrast;
            } else {
                log.info("数据库中没有该记录.");
                return null;
            }
        }
    }

}
