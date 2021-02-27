package xyz.a00000.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.dto.EssayTagParams;
import xyz.a00000.blog.bean.orm.EssayTag;
import xyz.a00000.blog.feign.EssayTagFeign;
import xyz.a00000.blog.service.EssayTagService;

@Service
@Slf4j
public class EssayTagServiceImpl implements EssayTagService {

    @Autowired
    private EssayTagFeign essayTagFeign;

    @Override
    public BaseActionResult<EssayTag> addNewTag(EssayTagParams params) {
        log.info("向远程服务请求增加一个标签.");
        BaseActionResult<EssayTag> result = essayTagFeign.addNewTag(params);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<Void> removeTag(Integer id) {
        log.info("向远程服务请求删除一条记录.");
        BaseActionResult<Void> result = essayTagFeign.removeTag(id);
        log.info("请求完成, 准备返回.");
        return result;
    }

}
