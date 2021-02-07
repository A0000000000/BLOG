package xyz.a00000.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.a00000.blog.bean.orm.Authority;
import xyz.a00000.blog.mapper.AuthorityMapper;
import xyz.a00000.blog.service.AuthorityService;

import java.io.Serializable;
import java.util.List;

@Service
@Slf4j
@Transactional
public class AuthorityServiceImpl extends BaseServiceImpl<Authority, AuthorityMapper> implements AuthorityService {

    @Override
    public List<Authority> selectUseCreatorId(Serializable creatorId) {
        log.info("根据用户id, 查询权限信息, id: " + creatorId);
        return u.selectByCreatorId(creatorId);
    }

}
