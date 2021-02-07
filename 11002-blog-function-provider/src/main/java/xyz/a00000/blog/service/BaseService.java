package xyz.a00000.blog.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;

public interface BaseService<T, U extends BaseMapper<T>> {

    Integer insert(T t);
    Integer update(T t);
    Integer delete(Serializable id);
    Integer count();
    T selectUseId(Serializable id);

}
