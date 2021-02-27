package xyz.a00000.blog.service;

import xyz.a00000.blog.bean.common.BaseActionResult;
import xyz.a00000.blog.bean.dto.EssayInitParamsBean;
import xyz.a00000.blog.bean.dto.EssayInitResultBean;

public interface EssayService {

    BaseActionResult<EssayInitResultBean> createEssay(EssayInitParamsBean params);

    BaseActionResult<String> updateEssay(EssayInitParamsBean params);

    BaseActionResult<Void> deleteEssayById(Integer id);

}
