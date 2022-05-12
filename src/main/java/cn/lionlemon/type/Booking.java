package cn.lionlemon.type;

import cn.lionlemon.entity.BookingEntity;
import cn.lionlemon.util.DateUtil;
import lombok.Data;

@Data
public class Booking {
    private Integer id;
    private User user;
    private Integer userId;
    private Event event;
    private Integer eventId;
    private String updatedAt;
    private String createdAt;

    public static Booking fromEntity(BookingEntity bookingEntity) {
        Booking booking = new Booking();
        booking.setId(bookingEntity.getId());
        booking.setUserId(bookingEntity.getUserId());
        booking.setEventId(bookingEntity.getEventId());
        booking.setCreatedAt(DateUtil.formatDateInISOString(bookingEntity.getCreatedAt()));
        booking.setUpdatedAt(DateUtil.formatDateInISOString(bookingEntity.getUpdatedAt()));
        return booking;
    }

}
