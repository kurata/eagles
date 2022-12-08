package com.eagles.booking.service;

import com.eagles.booking.api.BookingApiSchema;
import com.eagles.booking.domain.Booking;
import com.eagles.components.utilities.Helper;
import com.eagles.components.utilities.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookingHelper implements Helper<Booking, BookingApiSchema, Long> {

    private BookingHelper() {
        super();
    }

    @Override
    public BookingApiSchema toApiSchema(final Booking booking) {
        return new BookingApiSchema(booking.getId(), booking.getClientId(), booking.getRoomId(), booking.getStartDate(), booking.getEndDate());
    }

    @Override
    public Booking toDomainSchema(BookingApiSchema bookingApiSchema) {
        Booking booking = new Booking(bookingApiSchema.getId());
        booking.setClientId(bookingApiSchema.getClientId());
        booking.setRoomId(bookingApiSchema.getRoomId());
        booking.setStartDate(bookingApiSchema.getStartDate());
        booking.setEndDate(bookingApiSchema.getEndDate());
        return booking;
    }

    @Override
    public Booking mergeDomainSchema(final Booking originalObject, final BookingApiSchema newObjectSchema) {
        Booking mergedBooking = new Booking(originalObject);
        if(newObjectSchema.getStartDate() != null){
            mergedBooking.setStartDate(newObjectSchema.getStartDate());
        }
        if( newObjectSchema.getEndDate() != null) {
            mergedBooking.setEndDate(newObjectSchema.getEndDate());
        }
        return mergedBooking;
    }

    @Override
    public Long extractId(final BookingApiSchema newObjectSchema) {
        return newObjectSchema.getId();
    }

    public PageResult<BookingApiSchema> toPageResult(final Page<Booking> bookingPage) {
        return new PageResult<>(
                bookingPage.getContent().stream().map(this::toApiSchema).collect(Collectors.toList()),
                bookingPage.getTotalPages(),
                bookingPage.getTotalElements()
        );
    }
}
