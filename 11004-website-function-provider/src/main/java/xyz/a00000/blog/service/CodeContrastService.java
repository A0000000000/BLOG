package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.mapper.CodeContrastMapper;

public interface CodeContrastService extends BaseService<CodeContrast, CodeContrastMapper> {

    BaseServiceResult<PageBean<CodeContrast>> getCodeContrastByForm(PageForm<CodeContrast> form);
    BaseServiceResult<CodeContrast> createCodeContrast(CodeContrast codeContrast);
    BaseServiceResult<CodeContrast> updateCodeContrast(CodeContrast codeContrast);

}
