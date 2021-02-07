package xyz.a00000.blog.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.a00000.blog.service.BaseService;

import java.io.Serializable;

public class BaseServiceImpl<T, U extends BaseMapper<T>> implements BaseService<T, U> {

    @Autowired
    protected U u;

    @Override
    public Integer insert(T t) {
        return u.insert(t);
    }

    @Override
    public Integer update(T t) {
        return u.updateById(t);
    }

    @Override
    public Integer delete(Serializable id) {
        return u.deleteById(id);
    }

    @Override
    public Integer count() {
        return u.selectCount(null);
    }

    @Override
    public T selectUseId(Serializable id) {
        return u.selectById(id);
    }

}
