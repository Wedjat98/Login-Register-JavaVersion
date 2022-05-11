package cn.lionlemon.fetcher;

import cn.lionlemon.entity.EventEntity;
import cn.lionlemon.mapper.EventEntityMapper;
import cn.lionlemon.type.Event;
import cn.lionlemon.type.EventInput;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;
import java.util.stream.Collectors;


//simple Dgs things
@DgsComponent
public class EventDataFetcher {
    private final EventEntityMapper eventEntityMapper;

    public EventDataFetcher(EventEntityMapper eventEntityMapper) {
        this.eventEntityMapper = eventEntityMapper;
    }

    //how to insert creator?
    @DgsQuery
    public List<Event> events() {
        List<EventEntity> eventEntities = eventEntityMapper.selectList(new QueryWrapper<>());
        return eventEntities.stream().map(Event::fromEntity).collect(Collectors.toList());
    }

    //type and name must same to schema filed
    @DgsMutation
    public Event createEvent(@InputArgument(name = "eventInput") EventInput input) {
        EventEntity eventEntity = EventEntity.fromEventInput(input);
        eventEntityMapper.insert(eventEntity);
        return Event.fromEntity(eventEntity);
    }
}
