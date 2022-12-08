package com.eagles.booking.service;

import com.eagles.booking.api.BookingApiSchema;
import com.eagles.booking.api.GetBookingsRequest;
import com.eagles.booking.domain.Booking;
import com.eagles.booking.repository.BookingRepository;
import com.eagles.components.MessageKeys;
import com.eagles.components.utilities.AbstractCrudRestService;
import com.eagles.components.utilities.PageResult;
import com.eagles.exception.BusinessExceptionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

@Service
@Slf4j
public class BookingService extends AbstractCrudRestService<Booking, BookingApiSchema, Long> {

    private final BusinessExceptionFactory businessExceptionFactory;

    private final BookingRepository repository;

    private final BookingHelper helper;

    @Autowired
    public BookingService(BusinessExceptionFactory businessExceptionFactory, BookingRepository repository, BookingHelper helper) {
        super(businessExceptionFactory, repository, helper, Booking.class);
        this.businessExceptionFactory = businessExceptionFactory;
        this.repository = repository;
        this.helper = helper;
    }

    public Mono<PageResult<BookingApiSchema>> getByRequest(final GetBookingsRequest getBookingsRequest) {
        return Mono.just(this.helper.toPageResult(this.repository.findAll(getBookingsRequest.toPageRequest())));
    }


    public Mono<BookingApiSchema> create(final BookingApiSchema postClientRequest) {
        this.validateBookingRequest(postClientRequest);
        Collection<Booking> bookings = this.repository.findByRoomIdAndConflictDate(
                postClientRequest.getRoomId(),
                postClientRequest.getStartDate(),
                postClientRequest.getEndDate()
        );
        if (!bookings.isEmpty()) {
            return Mono.error(this.businessExceptionFactory.build(
                    HttpStatus.CONFLICT,
                    MessageKeys.BOOKING_ALREADY_EXISTS,
                    postClientRequest.getRoomId(), postClientRequest.getStartDate(), postClientRequest.getEndDate()
            ));
        }
        return super.post(postClientRequest);
    }

    public Mono<BookingApiSchema> update(final Long key, final BookingApiSchema postClientRequest) {
        validateBookingRequest(postClientRequest);
        return this.patch(key, postClientRequest);
    }

    public void validateBookingRequest(final BookingApiSchema postClientRequest) {
        LocalDate today = LocalDate.now();
        if (!today.isBefore(postClientRequest.getStartDate())) {
            throw this.businessExceptionFactory.build(
                    HttpStatus.BAD_REQUEST,
                    MessageKeys.BOOKING_NEED_START_AFTER_TODAY,
                    postClientRequest.getRoomId(), postClientRequest.getStartDate(), postClientRequest.getEndDate()
            );
        }
        if (today.until(postClientRequest.getStartDate(), ChronoUnit.DAYS) > 30) {
            throw this.businessExceptionFactory.build(
                    HttpStatus.BAD_REQUEST,
                    MessageKeys.BOOKING_MORE_THAN_THIRTY_DAYS_ADVANCE,
                    postClientRequest.getRoomId(), postClientRequest.getStartDate(), postClientRequest.getEndDate()
            );
        }
        if (postClientRequest.getStartDate().until(postClientRequest.getEndDate(), ChronoUnit.DAYS) > 3) {
            throw this.businessExceptionFactory.build(
                    HttpStatus.BAD_REQUEST,
                    MessageKeys.BOOKING_MORE_THAN_THREE_DAYS_LONG,
                    postClientRequest.getRoomId(), postClientRequest.getStartDate(), postClientRequest.getEndDate()
            );
        }
    }

}
