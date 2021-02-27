package xyz.a00000.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.dto.EssayInitParamsBean;
import xyz.a00000.blog.bean.dto.EssayInitResultBean;
import xyz.a00000.blog.feign.EssayFeign;
import xyz.a00000.blog.service.EssayService;

@Service
@Slf4j
public class EssayServiceImpl implements EssayService {

    @Autowired
    private EssayFeign essayFeign;

    @Override
    public BaseActionResult<EssayInitResultBean> createEssay(EssayInitParamsBean params) {
        log.info("向远程服务请求初始化一条记录.");
        BaseActionResult<EssayInitResultBean> result = essayFeign.createEssay(params);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<String> updateEssay(EssayInitParamsBean params) {
        log.info("向远程服务发起更新请求.");
        BaseActionResult<String> result = essayFeign.updateEssay(params);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<Void> deleteEssayById(Integer id) {
        log.info("向远程服务发起删除请求.");
        BaseActionResult<Void> result = essayFeign.deleteEssayById(id);
        log.info("请求完成, 准备返回.");
        return result;
    }

}
