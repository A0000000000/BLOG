package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.dto.EssayProxyBean;
import xyz.a00000.blog.bean.dto.EssayQueryBean;
import xyz.a00000.blog.bean.orm.EssayInfo;

public interface EssayService {

    BaseActionResult<PageBean<EssayQueryBean>> getEssayList(PageForm<EssayInfo> form);

    BaseActionResult<EssayProxyBean> getEssayData(Integer essayId, String password);

}
