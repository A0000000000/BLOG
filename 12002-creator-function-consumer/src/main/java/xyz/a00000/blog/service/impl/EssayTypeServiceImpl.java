package xyz.a00000.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.EssayType;
import xyz.a00000.blog.feign.EssayTypeFeign;
import xyz.a00000.blog.service.EssayTypeService;

import java.util.List;

@Service
@Slf4j
public class EssayTypeServiceImpl implements EssayTypeService {

    @Autowired
    private EssayTypeFeign essayTypeFeign;

    @Override
    public BaseActionResult<List<EssayType>> getAllEssayType() {
        log.info("向远程服务请求所有的类型.");
        BaseActionResult<List<EssayType>> result = essayTypeFeign.getAllEssayType();
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<PageBean<EssayType>> getEssayTypeByForm(PageForm<EssayType> form) {
        log.info("向远程服务请求分页数据.");
        BaseActionResult<PageBean<EssayType>> result = essayTypeFeign.getEssayTypeByForm(form);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<EssayType> createEssayType(EssayType type) {
        log.info("向远程服务发起创建请求.");
        BaseActionResult<EssayType> result = essayTypeFeign.createEssayType(type);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<EssayType> updateEssayType(EssayType type) {
        log.info("向远程服务发起更新请求.");
        BaseActionResult<EssayType> result = essayTypeFeign.updateEssayType(type);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<Void> deleteEssayTypeById(Integer id) {
        log.info("向远程服务发起删除请求.");
        BaseActionResult<Void> result = essayTypeFeign.deleteEssayTypeById(id);
        log.info("请求完成, 准备返回.");
        return result;
    }

}
