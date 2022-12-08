package com.eagles.booking;

import com.eagles.utils.AbstractApplicationTestStart;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

class DeleteBookingTest extends AbstractApplicationTestStart {

    @Test
    @Sql("/db/base_booking.sql")
    void shouldDeleteBookingTest() {
        webTestClient()
                .delete()
                .uri("/api/bookings/1")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void shouldDeleteBookingNotFoundTest() {
        webTestClient()
                .delete()
                .uri("/api/bookings/1")
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
