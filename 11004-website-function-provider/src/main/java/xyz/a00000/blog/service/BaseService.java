package xyz.a00000.blog.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.BaseServiceResult;

import java.io.Serializable;

public interface BaseService<T, U extends BaseMapper<T>> {

    BaseServiceResult<Integer> insert(T t);
    BaseServiceResult<Integer> update(T t);
    BaseServiceResult<Integer> delete(Serializable id);
    BaseServiceResult<Integer> count();
    BaseServiceResult<T> selectUseId(Serializable id);

}
