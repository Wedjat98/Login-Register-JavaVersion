package cn.lionlemon.fetcher;

import cn.lionlemon.custom.AuthContext;
import cn.lionlemon.entity.BookingEntity;
import cn.lionlemon.entity.EventEntity;
import cn.lionlemon.entity.UserEntity;
import cn.lionlemon.mapper.BookingEntityMapper;
import cn.lionlemon.mapper.EventEntityMapper;
import cn.lionlemon.mapper.UserEntityMapper;
import cn.lionlemon.type.Booking;
import cn.lionlemon.type.Event;
import cn.lionlemon.type.User;
import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.context.DgsContext;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
@Slf4j
public class BookingDataFetcher {

    private final BookingEntityMapper bookingEntityMapper;
    private final EventEntityMapper eventEntityMapper;

    private final UserEntityMapper userEntityMapper;

    public BookingDataFetcher(BookingEntityMapper bookingEntityMapper, EventEntityMapper eventEntityMapper, UserEntityMapper userEntityMapper) {
        this.bookingEntityMapper = bookingEntityMapper;
        this.eventEntityMapper = eventEntityMapper;
        this.userEntityMapper = userEntityMapper;
    }

    @DgsQuery
    public List<Booking> bookings() {
        return bookingEntityMapper.selectList(null)
                .stream()
                .map(Booking::fromEntity)
                .collect(Collectors.toList());
    }

    @DgsMutation
    public Booking bookingEvent(@InputArgument String eventId, DataFetchingEnvironment dfe) {
        AuthContext authContext = DgsContext.getCustomContext(dfe);
        authContext.ensureAuthenticated();

        UserEntity userEntity = authContext.getUserEntity();
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setUserId(userEntity.getId());
        bookingEntity.setEventId(Integer.parseInt(eventId));
        bookingEntity.setCreatedAt(new Date());
        bookingEntity.setUpdatedAt(new Date());

        bookingEntityMapper.insert(bookingEntity);
        return Booking.fromEntity(bookingEntity);
    }

    @DgsMutation
    public Event cancelBooking(@InputArgument(name = "bookingId") String bookingIdString,
                               DataFetchingEnvironment dfe) {
        AuthContext authContext = DgsContext.getCustomContext(dfe);
        authContext.ensureAuthenticated();
        Integer bookingId = Integer.parseInt(bookingIdString);
        BookingEntity bookingEntity = bookingEntityMapper.selectById(bookingId);
        if (bookingEntity == null)
            throw new RuntimeException(String.format("Booking with id %s does not exist", bookingIdString));


        Integer userId = bookingEntity.getUserId();
        UserEntity userEntity = authContext.getUserEntity();
        if (!userEntity.getId().equals(userId))
            throw new RuntimeException("You are not allowed to cancel other's booking !");
        bookingEntityMapper.deleteById(bookingId);

        Integer eventId = bookingEntity.getEventId();

        EventEntity eventEntity = eventEntityMapper.selectById(eventId);
        return Event.fromEntity(eventEntity);

    }


    @DgsData(parentType = "Booking")
    public User user(DgsDataFetchingEnvironment dfe) {
        Booking booking = dfe.getSource();
        UserEntity userEntity = userEntityMapper.selectById(booking.getUserId());
        return User.formEntity(userEntity);
    }

    @DgsData(parentType = "Booking")
    public Event event(DgsDataFetchingEnvironment dfe) {
        Booking booking = dfe.getSource();
        EventEntity eventEntity = eventEntityMapper.selectById(booking.getEventId());
        return Event.fromEntity(eventEntity);
    }
}
