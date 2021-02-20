package xyz.a00000.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xyz.a00000.blog.bean.orm.Email;

@Mapper
@Repository
public interface EmailMapper extends BaseMapper<Email> {
}