package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.AccessInfo;
import xyz.a00000.blog.mapper.AccessInfoMapper;

public interface AccessInfoService extends BaseService<AccessInfo, AccessInfoMapper> {

    BaseServiceResult<PageBean<AccessInfo>> getAccessInfoByForm(PageForm<AccessInfo> form);

}
