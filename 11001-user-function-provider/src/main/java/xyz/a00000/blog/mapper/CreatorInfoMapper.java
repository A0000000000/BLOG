package xyz.a00000.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xyz.a00000.blog.bean.orm.CreatorInfo;

@Mapper
@Repository
public interface CreatorInfoMapper extends BaseMapper<CreatorInfo> {
}
