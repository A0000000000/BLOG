package xyz.a00000.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.EssayType;

import java.util.List;

@Mapper
@Repository
public interface EssayTypeMapper extends BaseMapper<EssayType> {

    List<EssayType> selectByPage(@Param("form") PageForm<EssayType> form);

}
