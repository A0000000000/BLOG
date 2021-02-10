package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.dto.EssayInitResultBean;
import xyz.a00000.blog.bean.dto.EssayInitParamsBean;
import xyz.a00000.blog.bean.orm.Essay;
import xyz.a00000.blog.bean.proxy.UserDetailsBean;
import xyz.a00000.blog.mapper.EssayMapper;

public interface EssayService extends BaseService<Essay, EssayMapper> {

    BaseServiceResult<EssayInitResultBean> createEssay(EssayInitParamsBean params, UserDetailsBean currentUserDetails);

    BaseServiceResult<String> updateEssay(EssayInitParamsBean params, UserDetailsBean currentUserDetails);

}
