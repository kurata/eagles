package com.eagles.booking;

import com.eagles.utils.AbstractApplicationTestStart;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

class GetBookingTest extends AbstractApplicationTestStart {

    @Test
    void shouldGetBookingWithEmptyResultTest() {
        webTestClient()
                .get()
                .uri("/api/bookings")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("{\"result\":[],\"totalPages\":0,\"totalElements\":0}");
    }

    @Test
    @Sql("/db/base_booking.sql")
    void shouldGetBookingTest() {
        webTestClient()
                .get()
                .uri("/api/bookings")
                .exchange()
                .expectStatus()
                .isOk();
    }

}
