package xyz.a00000.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.orm.EssayTag;
import xyz.a00000.blog.feign.BlogFeign;
import xyz.a00000.blog.service.EssayTagService;

import java.util.List;

@Service
@Slf4j
public class EssayTagServiceImpl implements EssayTagService {

    @Autowired
    private BlogFeign blogFeign;

    @Override
    public BaseActionResult<List<EssayTag>> getEssayTags(Integer essayId) {
        log.info("向远程服务请求标签数据.");
        BaseActionResult<List<EssayTag>> result = blogFeign.getEssayTags(essayId);
        log.info("请求完成, 准备返回.");
        return result;
    }
}
