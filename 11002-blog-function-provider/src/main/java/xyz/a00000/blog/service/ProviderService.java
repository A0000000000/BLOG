package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.dto.EssayProxyBean;
import xyz.a00000.blog.bean.dto.EssayQueryBean;
import xyz.a00000.blog.bean.orm.EssayInfo;
import xyz.a00000.blog.mapper.EssayInfoMapper;

public interface ProviderService extends BaseService<EssayInfo, EssayInfoMapper> {

    BaseServiceResult<PageBean<EssayQueryBean>> getEssayList(PageForm<EssayInfo> form);

    BaseServiceResult<EssayProxyBean> getEssayData(EssayInfo essayInfo);

}
