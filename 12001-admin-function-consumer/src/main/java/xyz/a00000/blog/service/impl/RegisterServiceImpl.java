package xyz.a00000.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.feign.RegisterFeign;
import xyz.a00000.blog.service.RegisterService;

@Service
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterFeign registerFeign;

    @Override
    public BaseActionResult<String> generateRegisterId() {
        log.info("生成注册的密钥, 并返回结果.");
        return registerFeign.generateRegisterId();
    }
}
