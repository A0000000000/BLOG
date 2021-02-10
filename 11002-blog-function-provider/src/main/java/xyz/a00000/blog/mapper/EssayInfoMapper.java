package xyz.a00000.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.dto.EssayQueryBean;
import xyz.a00000.blog.bean.orm.EssayInfo;

import java.util.List;

@Mapper
@Repository
public interface EssayInfoMapper extends BaseMapper<EssayInfo> {

    List<EssayQueryBean> selectByPage(@Param("form") PageForm<EssayInfo> form);

}
