package xyz.a00000.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.mapper.CodeContrastMapper;
import xyz.a00000.blog.service.CodeContrastService;

@Service
@Transactional
@Slf4j
public class CodeContrastServiceImpl extends BaseServiceImpl<CodeContrast, CodeContrastMapper> implements CodeContrastService {

    @Override
    @HystrixCommand(fallbackMethod = "createCodeContrast_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<CodeContrast> createCodeContrast(CodeContrast codeContrast) {
        log.info("校验参数是否为空.");
        if (codeContrast == null || codeContrast.getCode() == null || codeContrast.getMessage() == null) {
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        log.info("检查code是否重复.");
        QueryWrapper<CodeContrast> qwCodeContrast = new QueryWrapper<>();
        qwCodeContrast.eq("code", codeContrast.getCode());
        CodeContrast one = u.selectOne(qwCodeContrast);
        if (one != null) {
            return BaseServiceResult.getFailedBean(new Exception("CODE_HAS_EXIST"), 11);
        }
        log.info("准备插入数据.");
        u.insert(codeContrast);
        log.info("插入完成, 准备返回数据.");
        return BaseServiceResult.getSuccessBean(codeContrast);
    }

    public BaseServiceResult<CodeContrast> createCodeContrast_fallback(CodeContrast codeContrast) {
        log.info("createCodeContrast发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

    @Override
    @HystrixCommand(fallbackMethod = "updateCodeContrast_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<CodeContrast> updateCodeContrast(CodeContrast codeContrast) {
        log.info("校验参数.");
        if (codeContrast == null || codeContrast.getId() == null) {
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        log.info("获得当前记录.");
        CodeContrast current = u.selectById(codeContrast.getId());
        if (current == null) {
            return BaseServiceResult.getFailedBean(new Exception("NO_RECORD"), 9);
        }
        log.info("设置带更新数据.");
        if (!StringUtils.isEmpty(codeContrast.getCode())) {
            current.setCode(codeContrast.getCode());
        }
        if (!StringUtils.isEmpty(codeContrast.getMessage())) {
            current.setMessage(codeContrast.getMessage());
        }
        log.info("持久化记录.");
        u.updateById(current);
        return BaseServiceResult.getSuccessBean(current);
    }

    public BaseServiceResult<CodeContrast> updateCodeContrast_fallback(CodeContrast codeContrast) {
        log.info("updateCodeContrast发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

}
