package cn.lionlemon.entity;

import cn.lionlemon.type.EventInput;
import cn.lionlemon.util.DateUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "tb_event")
public class EventEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private Date date;
    private Integer creatorId;

    public static EventEntity fromEventInput(EventInput input){
        EventEntity eventEntity = new EventEntity();
        eventEntity.setTitle(input.getTitle());
        eventEntity.setDescription(input.getDescription());
        eventEntity.setPrice(input.getPrice());
        eventEntity.setDate(DateUtil.convertISOStringToDate(input.getDate()));
        eventEntity.setCreatorId(input.getCreatorId());
        return eventEntity;

    }
}
