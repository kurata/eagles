package com.eagles.booking.controller;

import com.eagles.booking.api.BookingApiSchema;
import com.eagles.booking.api.BookingResource;
import com.eagles.booking.api.GetBookingsRequest;
import com.eagles.booking.service.BookingService;
import com.eagles.components.utilities.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Transactional
@Slf4j
public class BookingController implements BookingResource {

    private final BookingService service;

    @Autowired
    public BookingController(final BookingService bookingService) {
        this.service = bookingService;
    }

    @Override
    public Mono<PageResult<BookingApiSchema>> getBooks(final GetBookingsRequest getBookingsRequest) {
        return this.service.getByRequest(getBookingsRequest);
    }

    @Override
    public Mono<BookingApiSchema> postBook(final BookingApiSchema postClientRequest) {
        return this.service.create(postClientRequest);
    }

    @Override
    public Mono<BookingApiSchema> getBook(final Long key) {
        return this.service.getUnique(key);
    }

    @Override
    public Mono<BookingApiSchema> putBook(final Long key, final BookingApiSchema postClientRequest) {
        return this.service.update(key, postClientRequest);
    }

    @Override
    public Mono<BookingApiSchema> patchBook(Long key, BookingApiSchema postClientRequest) {
        return this.service.update(key, postClientRequest);
    }

    @Override
    public Mono<Void> deleteBook(Long key) {
        return this.service.delete(key);
    }
}
