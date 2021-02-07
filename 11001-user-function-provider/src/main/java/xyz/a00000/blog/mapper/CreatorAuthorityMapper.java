package xyz.a00000.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.a00000.blog.bean.orm.CreatorAuthority;

import java.util.List;

@Mapper
@Repository
public interface CreatorAuthorityMapper extends BaseMapper<CreatorAuthority> {

    Integer insertCreatorAuthorities(@Param("list") List<CreatorAuthority> list);

}
