package cn.lionlemon.fetcher;

import cn.lionlemon.entity.EventEntity;
import cn.lionlemon.entity.UserEntity;
import cn.lionlemon.mapper.EventEntityMapper;
import cn.lionlemon.mapper.UserEntityMapper;
import cn.lionlemon.type.Event;
import cn.lionlemon.type.EventInput;
import cn.lionlemon.type.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;


//simple Dgs things
@DgsComponent
@Slf4j
@RequiredArgsConstructor
public class EventDataFetcher {
    private final EventEntityMapper eventEntityMapper;
    private final UserEntityMapper userEntityMapper;

    //how to insert creator? solved 根据creatorID反查数据库
    @DgsQuery
    public List<Event> events() {
        List<EventEntity> eventEntities = eventEntityMapper.selectList(new QueryWrapper<>());
        return eventEntities.stream().map(eventEntity -> {
            Event event = Event.fromEntity(eventEntity);
            populateEventWithUser(event,eventEntity.getCreatorId());
            return event;
        }).collect(Collectors.toList());
    }

    //type and name must same to schema filed
    @DgsMutation
    public Event createEvent(@InputArgument(name = "eventInput") EventInput input) {
        EventEntity eventEntity = EventEntity.fromEventInput(input);
        eventEntityMapper.insert(eventEntity);
        Event newEvent = Event.fromEntity(eventEntity);
        populateEventWithUser(newEvent,eventEntity.getCreatorId());
        return newEvent;
    }

    private void populateEventWithUser(Event event,Integer userid){
        UserEntity userEntity = userEntityMapper.selectById(userid);
        User user = User.formEntity(userEntity);
        event.setCreator(user);
    }
}
