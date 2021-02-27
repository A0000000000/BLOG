package xyz.a00000.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.feign.EssayCommentFeign;
import xyz.a00000.blog.service.EssayCommentService;

@Service
@Slf4j
public class EssayCommentServiceImpl implements EssayCommentService {

    @Autowired
    private EssayCommentFeign essayCommentFeign;

    @Override
    public BaseActionResult<Void> removeComment(Integer id) {
        log.info("准备向远程服务请求删除一条记录.");
        BaseActionResult<Void> result = essayCommentFeign.removeComment(id);
        log.info("请求完成, 准备返回.");
        return result;
    }


}
