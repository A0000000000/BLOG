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
import xyz.a00000.blog.bean.orm.EssayInfo;
import xyz.a00000.blog.bean.orm.EssayType;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.mapper.EssayInfoMapper;
import xyz.a00000.blog.mapper.EssayTypeMapper;
import xyz.a00000.blog.service.EssayTypeService;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class EssayTypeServiceImpl extends BaseServiceImpl<EssayType, EssayTypeMapper> implements EssayTypeService {

    @Autowired
    private EssayInfoMapper essayInfoMapper;

    @Override
    @HystrixCommand(fallbackMethod = "getEssayType_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<PageBean<EssayType>> getEssayType(PageForm<EssayType> form) {
        log.info("获取类型列表.");
        QueryWrapper<EssayType> qwEssayType = new QueryWrapper<>();
        qwEssayType.eq("creator_id", form.getConditions().getCreatorId());
        Integer sum = u.selectCount(qwEssayType);
        log.info("获得类型数量: " + sum);
        log.info("构建返回数据对象.");
        PageBean<EssayType> bean = PageBean.createPageBean(form, sum);
        log.info("查询数据.");
        List<EssayType> data = u.selectByPage(form);
        bean.setData(data);
        return BaseServiceResult.getSuccessBean(bean);
    }

    public BaseServiceResult<PageBean<EssayType>> getEssayType_fallback(PageForm<Type> form) {
        log.info("getEssayType方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

    @Override
    @HystrixCommand(fallbackMethod = "createEssayType_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<EssayType> createEssayType(EssayType type, UserDetailsBean userDetailsBean) {
        log.info("创建新的类型.");
        log.info("检查参数是否合法.");
        if (StringUtils.isEmpty(type.getTypeName())) {
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        type.setCreatorId(userDetailsBean.getCreator().getId());
        log.info("检查类型是否存在.");
        QueryWrapper<EssayType> qwEssayType = new QueryWrapper<>();
        qwEssayType.eq("creator_id", type.getCreatorId());
        qwEssayType.eq("type_name", type.getTypeName());
        EssayType one = u.selectOne(qwEssayType);
        if (one != null) {
            log.info("类型已存在, 采取更新策略");
            if (!StringUtils.isEmpty(type.getMessage())) {
                one.setMessage(type.getMessage());
                u.updateById(one);
            }
            return BaseServiceResult.getSuccessBean(one);
        }
        log.info("类型不存在, 新建类型.");
        type.setCreateTime(new Date());
        u.insert(type);
        log.info("插入类型成功, 返回.");
        return BaseServiceResult.getSuccessBean(type);
    }

    public BaseServiceResult<EssayType> createEssayType_fallback(EssayType type, UserDetailsBean userDetailsBean) {
        log.info("createEssayType方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

    @Override
    @HystrixCommand(fallbackMethod = "updateEssayType_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<EssayType> updateEssayType(EssayType type, UserDetailsBean currentUserDetails) {
        log.info("检查参数是否合法.");
        if (type.getId() == null || (StringUtils.isEmpty(type.getTypeName()) && StringUtils.isEmpty(type.getMessage()))) {
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        EssayType one = u.selectById(type.getId());
        if (one == null) {
            log.info("待修改记录不存在.");
            return BaseServiceResult.getFailedBean(new Exception("NO_RECORD"), 9);
        }
        log.info("判断记录是否有权限修改.");
        if (!currentUserDetails.getCreator().getId().equals(one.getCreatorId())) {
            return BaseServiceResult.getFailedBean(new Exception("ACCESS_DENIED"), 7);
        }
        log.info("更新名称.");
        if (!StringUtils.isEmpty(type.getTypeName())) {
            one.setTypeName(type.getTypeName());
        }
        log.info("更新备注.");
        if (type.getMessage() != null) {
            one.setMessage(type.getMessage());
        }
        log.info("持久化更新.");
        u.updateById(one);
        return BaseServiceResult.getSuccessBean(one);
    }

    public BaseServiceResult<EssayType> updateEssayType_fallback(EssayType type, UserDetailsBean userDetailsBean) {
        log.info("updateEssayType方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

    @Override
    @HystrixCommand(fallbackMethod = "deleteEssayType_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<Void> deleteEssayType(EssayType type, UserDetailsBean currentUserDetails) {
        log.info("删除一条类型信息.");
        log.info("检查参数.");
        if (type.getId() == null) {
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        log.info("加载待删除类型数据, 判断是否有权限删除.");
        EssayType one = u.selectById(type.getId());
        if (one == null || !currentUserDetails.getCreator().getId().equals(one.getCreatorId())) {
            return BaseServiceResult.getFailedBean(new Exception("ACCESS_DENIED"), 7);
        }
        log.info("判断是否能删除.");
        QueryWrapper<EssayInfo> qwEssayType = new QueryWrapper<>();
        qwEssayType.eq("essay_type_id", type.getId());
        Integer num = essayInfoMapper.selectCount(qwEssayType);
        if (num != null && num.intValue() != 0) {
            return BaseServiceResult.getFailedBean(new Exception("HAS_DEPENDENT"), 10);
        }
        log.info("删除记录.");
        u.deleteById(one.getId());
        return BaseServiceResult.getSuccessBean(null);
    }

    public BaseServiceResult<Void> deleteEssayType_fallback(EssayType type, UserDetailsBean userDetailsBean) {
        log.info("deleteEssayType方法发生熔断.");
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

}
