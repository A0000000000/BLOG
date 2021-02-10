package xyz.a00000.blog.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.dto.EssayProxyBean;
import xyz.a00000.blog.bean.dto.EssayQueryBean;
import xyz.a00000.blog.bean.orm.Essay;
import xyz.a00000.blog.bean.orm.EssayInfo;
import xyz.a00000.blog.mapper.EssayInfoMapper;
import xyz.a00000.blog.mapper.EssayMapper;
import xyz.a00000.blog.mapper.EssayTypeMapper;
import xyz.a00000.blog.service.ProviderService;

import java.util.List;

@Service
@Slf4j
@Transactional
public class ProviderServiceImpl extends BaseServiceImpl<EssayInfo, EssayInfoMapper> implements ProviderService {

    @Autowired
    private EssayMapper essayMapper;
    @Autowired
    private EssayTypeMapper essayTypeMapper;

    @Override
    @HystrixCommand(fallbackMethod = "getEssayList_fullback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<PageBean<EssayQueryBean>> getEssayList(PageForm<EssayInfo> form) {
        log.info("查询随笔列表.");
        log.info("校验数据.");
        if (form.getConditions().getCreatorId() == null) {
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        log.info("构建返回对象.");
        QueryWrapper<EssayInfo> qwEssayInfo = new QueryWrapper<>();
        qwEssayInfo.eq("creator_id", form.getConditions().getCreatorId());
        Integer num = u.selectCount(qwEssayInfo);
        PageBean<EssayQueryBean> bean = new PageBean<>();
        log.info("判断每页数据是否合法.");
        Integer count = form.getCount();
        if (count == null || count == 0) {
            count = 10;
        }
        log.info("设置总数量, 每页数量.");
        bean.setSum(num);
        bean.setCount(count);
        bean.setTotal((num + count - 1) / count);
        log.info("检查页数是否合法.");
        Integer page = form.getPage();
        if (page > bean.getTotal()) {
            page = bean.getTotal();
        }
        if (page == null || page < 1) {
            page = 1;
        }
        log.info("设置待查询的页数.");
        bean.setPage(page);
        form.setCount(count);
        form.setPage(page);
        List<EssayQueryBean> data = u.selectByPage(form);
        bean.setData(data);
        return BaseServiceResult.getSuccessBean(bean);
    }

    public BaseServiceResult<PageBean<EssayQueryBean>> getEssayList_fullback(PageForm<EssayInfo> form) {
        log.info("getEssayList方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FULLBACK"), 3);
    }

    @Override
    @HystrixCommand(fallbackMethod = "getEssayData_fullback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<EssayProxyBean> getEssayData(EssayInfo essayInfo) {
        log.info("获取随笔数据.");
        log.info("校验参数是否合法.");
        if (essayInfo.getEssayId() == null) {
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        log.info("加载随笔数据.");
        Essay essay = essayMapper.selectById(essayInfo.getEssayId());
        if (essay == null) {
            return BaseServiceResult.getFailedBean(new Exception("NO_RECORD"), 9);
        }
        QueryWrapper<EssayInfo> qwEssayInfo = new QueryWrapper<>();
        qwEssayInfo.eq("essay_id", essay.getId());
        EssayInfo one = u.selectOne(qwEssayInfo);
        if (!StringUtils.isEmpty(one.getPassword()) && !one.getPassword().equals(essayInfo.getPassword())) {
            return BaseServiceResult.getFailedBean(new Exception("ACCESS_DENIED"), 7);
        }

        return null;
    }

    public BaseServiceResult<EssayProxyBean> getEssayData_fullback(EssayInfo essayInfo) {
        log.info("getEssayData方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FULLBACK"), 3);
    }

}
