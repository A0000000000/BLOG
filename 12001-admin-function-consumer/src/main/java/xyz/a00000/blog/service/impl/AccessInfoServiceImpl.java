package xyz.a00000.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.AccessInfo;
import xyz.a00000.blog.feign.AccessInfoFeign;
import xyz.a00000.blog.service.AccessInfoService;

@Service
@Slf4j
public class AccessInfoServiceImpl implements AccessInfoService {

    @Autowired
    private AccessInfoFeign accessInfoFeign;

    @Override
    public BaseActionResult<AccessInfo> getAccessInfoById(Integer id) {
        log.info("根据id, 向远程服务请求一条记录.");
        BaseActionResult<AccessInfo> result = accessInfoFeign.getAccessInfoById(id);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<PageBean<AccessInfo>> getAccessInfoByForm(PageForm<AccessInfo> form) {
        log.info("根据分页信息, 向远程服务查询一条记录.");
        BaseActionResult<PageBean<AccessInfo>> result = accessInfoFeign.getAccessInfoByForm(form);
        log.info("请求完成, 准备返回.");
        return result;

    }
}
