package xyz.a00000.blog.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.service.BaseService;

import java.io.Serializable;

public class BaseServiceImpl<T, U extends BaseMapper<T>> implements BaseService<T, U> {

    @Autowired
    protected U u;

    @Override
    public BaseServiceResult<Integer> insert(T t) {
        return BaseServiceResult.getSuccessBean(u.insert(t));
    }

    @Override
    public BaseServiceResult<Integer> update(T t) {
        return BaseServiceResult.getSuccessBean(u.updateById(t));
    }

    @Override
    public BaseServiceResult<Integer> delete(Serializable id) {
        return BaseServiceResult.getSuccessBean(u.deleteById(id));
    }

    @Override
    public BaseServiceResult<Integer> count() {
        return BaseServiceResult.getSuccessBean(u.selectCount(null));
    }

    @Override
    public BaseServiceResult<T> selectUseId(Serializable id) {
        return BaseServiceResult.getSuccessBean(u.selectById(id));
    }

}
