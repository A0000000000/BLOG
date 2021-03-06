package xyz.a00000.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.a00000.blog.bean.common.PageForm;
import xyz.a00000.blog.bean.orm.CodeContrast;

import java.util.List;

@Mapper
@Repository
public interface CodeContrastMapper extends BaseMapper<CodeContrast> {

    List<CodeContrast> selectByForm(@Param("form") PageForm<CodeContrast> form);

}
