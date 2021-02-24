package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.CodeContrast;

public interface CodeContrastService {

    BaseActionResult<CodeContrast> getCodeContrastById(Integer id);

    BaseActionResult<PageBean<CodeContrast>> getCodeContrastByForm(PageForm<CodeContrast> form);

    BaseActionResult<CodeContrast> createCodeContrast(CodeContrast codeContrast);

    BaseActionResult<CodeContrast> updateCodeContrast(CodeContrast codeContrast);

    BaseActionResult<Void> deleteCodeContrast(Integer id);

}
