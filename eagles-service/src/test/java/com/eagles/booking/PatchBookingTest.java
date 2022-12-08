package com.eagles.booking;

import com.eagles.booking.api.BookingApiSchema;
import com.eagles.utils.AbstractApplicationTestStart;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

class PatchBookingTest extends AbstractApplicationTestStart {

    @Test
    @Sql("/db/base_booking.sql")
    void shouldPatchCustomerUpdatingNameTest() {
        LocalDate today = LocalDate.now();
        webTestClient()
                .patch()
                .uri("/api/bookings/1")
                .bodyValue(new BookingApiSchema(null, 1L, 1L, today.plusDays(11), today.plusDays(14)))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("{\"id\":1,\"clientId\":1,\"roomId\":1,\"startDate\":\"" + today.plusDays(11) + "\",\"endDate\":\"" + today.plusDays(14) + "\"}");
    }

    @Test
    void shouldPatchCustomerUpdatingNameNotFoundTest() {
        LocalDate today = LocalDate.now();
        webTestClient()
                .patch()
                .uri("/api/bookings/1")
                .bodyValue(new BookingApiSchema(null, 1L, 1L, today.plusDays(14), today.plusDays(16)))
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @Sql("/db/base_booking.sql")
    void shouldPatchCustomerUpdatingStartDateTest() {
        LocalDate today = LocalDate.now();
        webTestClient()
                .patch()
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
    void shouldPatchCustomerUpdatingEndDateTest() {
        LocalDate today = LocalDate.now();
        webTestClient()
                .patch()
                .uri("/api/bookings/1")
                .bodyValue(new BookingApiSchema(null, 1L, 1L, today.plusDays(10), today.plusDays(12)))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("{\"id\":1,\"clientId\":1,\"roomId\":1,\"startDate\":\"" + today.plusDays(10) + "\",\"endDate\":\"" + today.plusDays(12) + "\"}");
    }

}
