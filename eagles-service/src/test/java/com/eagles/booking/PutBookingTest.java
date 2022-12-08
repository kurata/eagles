package com.eagles.booking;

import com.eagles.booking.api.BookingApiSchema;
import com.eagles.utils.AbstractApplicationTestStart;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

class PutBookingTest extends AbstractApplicationTestStart {

    @Test
    @Sql("/db/base_booking.sql")
    void shouldPutCustomerUpdatingNameTest() {
        LocalDate today = LocalDate.now();
        webTestClient()
                .put()
                .uri("/api/bookings/1")
                .bodyValue(new BookingApiSchema(null, 1L, 1L, today.plusDays(11), today.plusDays(14)))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("{\"id\":1,\"clientId\":1,\"roomId\":1,\"startDate\":\"" + today.plusDays(11) + "\",\"endDate\":\"" + today.plusDays(14) + "\"}");
    }

    @Test
    void shouldPutCustomerUpdatingNameNotFoundTest() {
        LocalDate today = LocalDate.now();
        webTestClient()
                .put()
                .uri("/api/bookings/1")
                .bodyValue(new BookingApiSchema(null, 1L, 1L, today.plusDays(14), today.plusDays(16)))
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @Sql("/db/base_booking.sql")
    void shouldPutCustomerUpdatingStartDateTest() {
        LocalDate today = LocalDate.now();
        webTestClient()
                .put()
                .uri("/api/bookings/1")
                .bodyValue(new BookingApiSchema(null, 1L, 1L, today.plusDays(11), today.plusDays(13)))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("{\"id\":1,\"clientId\":1,\"roomId\":1,\"startDate\":\"" + today.plusDays(11) + "\",\"endDate\":\"" + today.plusDays(13) + "\"}");
    }

    @Test
    @Sql("/db/base_booking.sql")
    void shouldPutCustomerUpdatingEndDateTest() {
        LocalDate today = LocalDate.now();
        webTestClient()
                .put()
                .uri("/api/bookings/1")
                .bodyValue(new BookingApiSchema(null, 1L, 1L, today.plusDays(10), today.plusDays(12)))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("{\"id\":1,\"clientId\":1,\"roomId\":1,\"startDate\":\"" + today.plusDays(10) + "\",\"endDate\":\"" + today.plusDays(12) + "\"}");
    }

}
