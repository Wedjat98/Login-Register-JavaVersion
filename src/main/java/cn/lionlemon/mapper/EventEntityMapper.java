package cn.lionlemon.mapper;

import cn.lionlemon.entity.EventEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EventEntityMapper extends BaseMapper<EventEntity> {
    abstract boolean isClosed();
}
