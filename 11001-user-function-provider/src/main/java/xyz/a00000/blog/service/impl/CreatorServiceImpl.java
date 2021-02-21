package xyz.a00000.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.dto.RegisterParams;
import xyz.a00000.blog.bean.dto.UserView;
import xyz.a00000.blog.bean.orm.Authority;
import xyz.a00000.blog.bean.orm.Creator;
import xyz.a00000.blog.bean.orm.CreatorAuthority;
import xyz.a00000.blog.bean.orm.CreatorInfo;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.mapper.AuthorityMapper;
import xyz.a00000.blog.mapper.CreatorAuthorityMapper;
import xyz.a00000.blog.mapper.CreatorInfoMapper;
import xyz.a00000.blog.mapper.CreatorMapper;
import xyz.a00000.blog.service.CreatorService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class CreatorServiceImpl extends BaseServiceImpl<Creator, CreatorMapper> implements CreatorService {

    @Autowired
    private RedisTemplate<String, List<String>> redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CreatorInfoMapper creatorInfoMapper;
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private CreatorAuthorityMapper creatorAuthorityMapper;

    @Override
    @HystrixCommand(fallbackMethod = "register_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<UserView> register(RegisterParams params) {
        log.info("执行正常的注册流程.");
        log.info("判断注册Token是否为空.");
        if (StringUtils.isEmpty(params.getToken())) {
            log.info("注册Token为空.");
            return BaseServiceResult.getFailedBean(new Exception("INVALID_TOKEN"), 4);
        }
        log.info("判断Token是否有效.");
        if (!redisTemplate.hasKey(params.getToken())) {
            log.info("Token无效");
            return BaseServiceResult.getFailedBean(new Exception("INVALID_TOKEN"), 4);
        }
        ValueOperations<String, List<String>> ops = redisTemplate.opsForValue();
        List<String> authority = ops.get(params.getToken());
        log.info("从Redis中取得注册信息, Token: " + params.getToken() + ", 权限: " + Arrays.toString(authority.toArray()));
        Creator creator = new Creator(params);
        log.info("判断用户名或密码是否为空.");
        if (StringUtils.isEmpty(creator.getUsername()) || StringUtils.isEmpty(creator.getPassword())) {
            log.info("用户名为空.");
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        log.info("加密密码.");
        creator.setPassword(passwordEncoder.encode(creator.getPassword()));
        log.info("查询用户名是否已存在.");
        QueryWrapper<Creator> qw = new QueryWrapper<>();
        qw.eq("username", creator.getUsername());
        Creator current = u.selectOne(qw);
        if (current != null) {
            log.info("已存在, 注册失败返回.");
            return BaseServiceResult.getFailedBean(new Exception("USERNAME_HAS_EXIST"), 6);
        }
        u.insert(creator);
        log.info("构建用户额外信息.");
        CreatorInfo info = new CreatorInfo(params);
        info.setCreatorId(creator.getId());
        creatorInfoMapper.insert(info);
        if (authority.isEmpty()) {
            log.info("从Redis中删除已使用的Token.");
            redisTemplate.delete(params.getToken());
            log.info("注册成功, 返回.");
            return BaseServiceResult.getSuccessBean(new UserView(creator, info, null));
        }
        log.info("设置用户身份.");
        List<Authority> authorities = authorityMapper.selectByNames(authority);
        List<CreatorAuthority> list = authorities.stream().map(item -> new CreatorAuthority(null, creator.getId(), item.getId())).collect(Collectors.toList());
        creatorAuthorityMapper.insertCreatorAuthorities(list);
        log.info("从Redis中删除已使用的Token.");
        redisTemplate.delete(params.getToken());
        log.info("注册成功, 返回.");
        creator.setPassword(null);
        log.info("清空密码, 将用户信息返回.");
        return BaseServiceResult.getSuccessBean(new UserView(creator, info, authorities));
    }

    public BaseServiceResult<UserView> register_fallback(RegisterParams params) {
        log.info("register触发熔断, 参数: " + params);
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

    @Override
    @HystrixCommand(fallbackMethod = "updateCreatorInfo_fallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public BaseServiceResult<UserView> updateCreatorInfo(RegisterParams params, UserDetailsBean currentUserDetails) {
        log.info("执行正常的用户信息修改流程.");
        if (!StringUtils.isEmpty(params.getPassword())) {
            log.info("密码需要修改, 进入修改密码流程.");
            Creator creator = currentUserDetails.getCreator();
            creator.setPassword(passwordEncoder.encode(params.getPassword()));
            u.updateById(creator);
        }
        log.info("修改用户详细信息.");
        CreatorInfo creatorInfo = currentUserDetails.getCreatorInfo();
        if (params.getBirthday() != null) {
            creatorInfo.setBirthday(params.getBirthday());
        }
        if (!StringUtils.isEmpty(params.getEmail())) {
            creatorInfo.setEmail(params.getEmail());
        }
        if (!StringUtils.isEmpty(params.getMessage())) {
            creatorInfo.setMessage(params.getMessage());
        }
        creatorInfoMapper.updateById(creatorInfo);
        log.info("修改成功, 返回用户信息.");
        currentUserDetails.getCreator().setPassword(null);
        return BaseServiceResult.getSuccessBean(new UserView(currentUserDetails.getCreator(), creatorInfo, null));
    }

    public BaseServiceResult<UserView> updateCreatorInfo_fallback(RegisterParams params, UserDetailsBean currentUserDetails) {
        log.info("updateCreatorInfo触发熔断, 参数: " + params);
        return BaseServiceResult.getFailedBean(new Exception("SERVICE_FALLBACK"), 3);
    }

}
