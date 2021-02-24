package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.AccessInfo;

public interface AccessInfoService {

    BaseActionResult<AccessInfo> getAccessInfoById(Integer id);

    BaseActionResult<PageBean<AccessInfo>> getAccessInfoByForm(PageForm<AccessInfo> form);

}
