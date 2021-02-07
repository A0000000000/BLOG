package xyz.a00000.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.a00000.blog.bean.orm.CreatorInfo;
import xyz.a00000.blog.mapper.CreatorInfoMapper;
import xyz.a00000.blog.service.CreatorInfoService;

@Service
@Slf4j
@Transactional
public class CreatorInfoServiceImpl extends BaseServiceImpl<CreatorInfo, CreatorInfoMapper> implements CreatorInfoService {

    @Override
    public CreatorInfo selectUseCreatorId(Integer creatorId) {
        log.info("根据用户id, 查询用户信息, id: " + creatorId);
        QueryWrapper<CreatorInfo> qw = new QueryWrapper<>();
        qw.eq("creator_id", creatorId);
        return u.selectOne(qw);
    }

}
