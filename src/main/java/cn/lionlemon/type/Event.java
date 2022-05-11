package cn.lionlemon.type;

import cn.lionlemon.entity.EventEntity;
import cn.lionlemon.util.DateUtil;
import lombok.Data;

@Data
public class Event {
    private String id;
    private String title;
    private String description;
    private Double price;
    private String date;
    private User creator;
    private Integer creatorId;

    public static Event fromEntity(EventEntity eventEntity){
        Event event = new Event();
        event.setId(eventEntity.getId().toString());
        event.setTitle(eventEntity.getTitle());
        event.setDescription(eventEntity.getDescription());
        event.setPrice(eventEntity.getPrice());
        event.setDate(DateUtil.formatDateInISOString(eventEntity.getDate()));
        event.setCreatorId(eventEntity.getCreatorId());
        return event;
    }
}
