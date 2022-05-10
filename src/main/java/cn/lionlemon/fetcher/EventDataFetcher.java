package cn.lionlemon.fetcher;

import cn.lionlemon.type.Event;
import cn.lionlemon.type.EventInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


//simple Dgs things
@DgsComponent
public class EventDataFetcher {
    private List<Event> events = new ArrayList<>();


    @DgsQuery
    public List<Event> events() {
        return events;
    }

    //type and name must same to schema filed
    @DgsMutation
    public Event createEvent(@InputArgument(name = "eventInput") EventInput input) {
        Event newEvent = new Event();
        newEvent.setId(UUID.randomUUID().toString());
        newEvent.setTitle(input.getTitle());
        newEvent.setDescription(input.getDescription());
        newEvent.setPrice(input.getPrice());
        newEvent.setDate(input.getDate());
        events.add(newEvent);
        return newEvent;
    }
}
