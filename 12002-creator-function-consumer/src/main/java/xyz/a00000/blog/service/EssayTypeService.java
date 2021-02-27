package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.EssayType;

import java.util.List;

public interface EssayTypeService {

    BaseActionResult<List<EssayType>> getAllEssayType();

    BaseActionResult<PageBean<EssayType>> getEssayTypeByForm(PageForm<EssayType> form);

    BaseActionResult<EssayType> createEssayType(EssayType type);

    BaseActionResult<EssayType> updateEssayType(EssayType type);

    BaseActionResult<Void> deleteEssayTypeById(Integer id);

}
