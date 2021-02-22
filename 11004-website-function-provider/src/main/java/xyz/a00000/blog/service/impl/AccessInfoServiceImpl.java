package xyz.a00000.blog.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.AccessInfo;
import xyz.a00000.blog.mapper.AccessInfoMapper;
import xyz.a00000.blog.service.AccessInfoService;

import java.util.List;

@Slf4j
@Transactional
@Service
public class AccessInfoServiceImpl extends BaseServiceImpl<AccessInfo, AccessInfoMapper> implements AccessInfoService {

    @Override
    @HystrixCommand(fallbackMethod = "getAccessInfoByForm_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<PageBean<AccessInfo>> getAccessInfoByForm(PageForm<AccessInfo> form) {
        log.info("根据分页信息查询分页数据.");
        log.info("加载数据条数, 构建返回对象.");
        Integer sum = u.selectCount(null);
        PageBean<AccessInfo> bean = PageBean.createPageBean(form, sum);
        log.info("查询具体数据.");
        List<AccessInfo> data = u.selectByForm(form);
        log.info("封装返回对象, 并返回.");
        bean.setData(data);
        return BaseServiceResult.getSuccessBean(bean);
    }

    public BaseServiceResult<PageBean<AccessInfo>> getAccessInfoByForm_fallback(PageForm<AccessInfo> form) {
        log.info("getAccessInfoByForm发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

}
