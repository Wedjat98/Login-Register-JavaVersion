package cn.lionlemon.fetcher;

import cn.lionlemon.custom.AuthContext;
import cn.lionlemon.entity.EventEntity;
import cn.lionlemon.entity.UserEntity;
import cn.lionlemon.mapper.EventEntityMapper;
import cn.lionlemon.mapper.UserEntityMapper;
import cn.lionlemon.type.Event;
import cn.lionlemon.type.EventInput;
import cn.lionlemon.type.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.context.DgsContext;
import graphql.schema.DataFetchingEnvironment;
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
        return eventEntities.stream().map(Event::fromEntity).collect(Collectors.toList());
    }

    //type and name must same to schema filed
    //only Authed user can use createEvent
    @DgsMutation
    public Event createEvent(@InputArgument(name = "eventInput") EventInput input, DataFetchingEnvironment dfe) {

        AuthContext authContext = DgsContext.getCustomContext(dfe);
        authContext.ensureAuthenticated();
        EventEntity eventEntity = EventEntity.fromEventInput(input);
        eventEntity.setCreatorId(authContext.getUserEntity().getId());
        eventEntityMapper.insert(eventEntity);
        //        populateEventWithUser(newEvent, eventEntity.getCreatorId());
        return Event.fromEntity(eventEntity);
    }

    @DgsData(parentType = "Event")
    public User creator(DgsDataFetchingEnvironment dfe) {
        Event event = dfe.getSource();
        UserEntity userEntity = userEntityMapper.selectById(event.getCreatorId());
        return User.formEntity(userEntity);
    }

    /*did not use the resolver way (useless) if front end do not need ,do not return
    private void populateEventWithUser(Event event,Integer userid){
       UserEntity userEntity = userEntityMapper.selectById(userid);
        User user = User.formEntity(userEntity);
        event.setCreator(user);
    }
    */
}

