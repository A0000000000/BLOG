package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.EssayType;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.mapper.EssayTypeMapper;

import java.util.List;

public interface EssayTypeService extends BaseService<EssayType, EssayTypeMapper> {

    BaseServiceResult<List<EssayType>> selectAllEssayType(UserDetailsBean currentUserDetails);

    BaseServiceResult<PageBean<EssayType>> getEssayTypeByForm(PageForm<EssayType> form);

    BaseServiceResult<EssayType> createEssayType(EssayType type, UserDetailsBean userDetailsBean);

    BaseServiceResult<EssayType> updateEssayType(EssayType type, UserDetailsBean currentUserDetails);

    BaseServiceResult<Void> deleteEssayTypeById(Integer id, UserDetailsBean currentUserDetails);

}
