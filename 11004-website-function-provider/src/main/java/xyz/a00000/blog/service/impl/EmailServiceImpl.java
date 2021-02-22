package xyz.a00000.blog.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.Email;
import xyz.a00000.blog.mapper.EmailMapper;
import xyz.a00000.blog.service.EmailService;

import java.util.List;

@Service
@Transactional
@Slf4j
public class EmailServiceImpl extends BaseServiceImpl<Email, EmailMapper> implements EmailService {


    @Override
    @HystrixCommand(fallbackMethod = "getEmailByForm_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<PageBean<Email>> getEmailByForm(PageForm<Email> form) {
        log.info("通过分页数据, 进行分页查询.");
        log.info("获取记录数量.");
        Integer sum = u.selectCount(null);
        log.info("构建分页结果.");
        PageBean<Email> bean = PageBean.createPageBean(form, sum);
        log.info("查询具体数据.");
        List<Email> data = u.selectByForm(form);
        log.info("封装数据并返回.");
        bean.setData(data);
        return BaseServiceResult.getSuccessBean(bean);
    }

    public BaseServiceResult<PageBean<Email>> getEmailByForm_fallback(PageForm<Email> form) {
        log.info("getEmailByForm发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

}
