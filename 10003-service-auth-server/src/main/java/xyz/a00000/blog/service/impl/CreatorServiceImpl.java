package xyz.a00000.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.a00000.blog.bean.orm.Creator;
import xyz.a00000.blog.mapper.CreatorMapper;
import xyz.a00000.blog.service.CreatorService;

@Service
@Slf4j
@Transactional
public class CreatorServiceImpl extends BaseServiceImpl<Creator, CreatorMapper> implements CreatorService {

    @Override
    public Creator selectUseUsername(String username) {
        log.info("根据用户名, 获得用户信息, username: " + username);
        QueryWrapper<Creator> qw = new QueryWrapper<>();
        qw.eq("username", username);
        return u.selectOne(qw);
    }

}
