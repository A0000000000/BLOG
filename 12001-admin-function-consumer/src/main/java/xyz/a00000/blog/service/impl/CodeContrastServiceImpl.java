package xyz.a00000.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.CodeContrast;
import xyz.a00000.blog.feign.CodeContrastFeign;
import xyz.a00000.blog.service.CodeContrastService;

@Slf4j
@Service
public class CodeContrastServiceImpl implements CodeContrastService {

    @Autowired
    private CodeContrastFeign codeContrastFeign;

    @Override
    public BaseActionResult<CodeContrast> getCodeContrastById(Integer id) {
        log.info("根据id向远程服务请求一条记录.");
        BaseActionResult<CodeContrast> result = codeContrastFeign.getCodeContrastById(id);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<PageBean<CodeContrast>> getCodeContrastByForm(PageForm<CodeContrast> form) {
        log.info("根据分页信息, 向远程服务请求分页数据.");
        BaseActionResult<PageBean<CodeContrast>> result = codeContrastFeign.getCodeContrastByForm(form);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<CodeContrast> createCodeContrast(CodeContrast codeContrast) {
        log.info("向远程服务添加一条新记录.");
        BaseActionResult<CodeContrast> result = codeContrastFeign.createCodeContrast(codeContrast);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<CodeContrast> updateCodeContrast(CodeContrast codeContrast) {
        log.info("向远程服务更新一条记录.");
        BaseActionResult<CodeContrast> result = codeContrastFeign.updateCodeContrast(codeContrast);
        log.info("请求完成, 准备返回.");
        return result;
    }

    @Override
    public BaseActionResult<Void> deleteCodeContrast(Integer id) {
        log.info("向远程服务中删除一条记录.");
        BaseActionResult<Void> result = codeContrastFeign.deleteCodeContrast(id);
        log.info("请求完成, 准备返回.");
        return result;
    }

}
