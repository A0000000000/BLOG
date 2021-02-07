package xyz.a00000.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.a00000.blog.bean.orm.Authority;

import java.io.Serializable;
import java.util.List;

@Mapper
@Repository
public interface AuthorityMapper extends BaseMapper<Authority> {

    List<Authority> selectByCreatorId(@Param("creatorId") Serializable creatorId);

}
