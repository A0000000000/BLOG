package xyz.a00000.blog.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.a00000.blog.bean.common.BaseServiceResult;
import xyz.a00000.blog.bean.common.PageBean;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.dto.EssayQueryBean;
import xyz.a00000.blog.bean.orm.EssayInfo;
import xyz.a00000.blog.mapper.EssayInfoMapper;
import xyz.a00000.blog.service.ProviderService;

import java.util.List;

@Service
@Slf4j
@Transactional
public class ProviderServiceImpl extends BaseServiceImpl<EssayInfo, EssayInfoMapper> implements ProviderService {

    @Override
    public BaseServiceResult<PageBean<EssayQueryBean>> getEssayList(PageForm<EssayInfo> form) {
        log.info("查询随笔列表.");
        log.info("校验数据.");
        if (form.getConditions().getCreatorId() == null) {
            return BaseServiceResult.getFailedBean(new Exception("EMPTY_ARGS"), 5);
        }
        log.info("构建返回对象.");
        QueryWrapper<EssayInfo> qwEssayInfo = new QueryWrapper<>();
        qwEssayInfo.eq("creator_id", form.getConditions().getCreatorId());
        Integer num = u.selectCount(qwEssayInfo);
        PageBean<EssayQueryBean> bean = new PageBean<>();
        log.info("判断每页数据是否合法.");
        Integer count = form.getCount();
        if (count == null || count == 0) {
            count = 10;
        }
        log.info("设置总数量, 每页数量.");
        bean.setSum(num);
        bean.setCount(count);
        bean.setTotal((num + count - 1) / count);
        log.info("检查页数是否合法.");
        Integer page = form.getPage();
        if (page > bean.getTotal()) {
            page = bean.getTotal();
        }
        if (page == null || page < 1) {
            page = 1;
        }
        log.info("设置待查询的页数.");
        bean.setPage(page);
        form.setCount(count);
        form.setPage(page);
        List<EssayQueryBean> data = u.selectByPage(form);
        bean.setData(data);
        return BaseServiceResult.getSuccessBean(bean);
    }

}
