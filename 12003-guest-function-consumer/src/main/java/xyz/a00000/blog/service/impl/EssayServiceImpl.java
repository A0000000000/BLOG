package xyz.a00000.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.dto.EssayProxyBean;
import xyz.a00000.blog.bean.dto.EssayQueryBean;
import xyz.a00000.blog.bean.orm.EssayInfo;
import xyz.a00000.blog.feign.BlogFeign;
import xyz.a00000.blog.service.EssayService;

@Service
@Slf4j
public class EssayServiceImpl implements EssayService {

    @Autowired
    private BlogFeign blogFeign;

    @Override
    public BaseActionResult<PageBean<EssayQueryBean>> getEssayList(PageForm<EssayInfo> form) {
        log.info("向远程服务请求分页数据.");
        BaseActionResult<PageBean<EssayQueryBean>> result = blogFeign.getEssayList(form);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<EssayProxyBean> getEssayData(Integer essayId, String password) {
        log.info("向远程服务请求一条记录.");
        BaseActionResult<EssayProxyBean> result = blogFeign.getEssayData(essayId, password);
        log.info("请求完成, 准备返回.");
        return result;
    }


}
