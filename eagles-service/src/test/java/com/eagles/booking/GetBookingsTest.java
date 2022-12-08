package com.eagles.booking;

import com.eagles.utils.AbstractApplicationTestStart;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

class GetBookingsTest extends AbstractApplicationTestStart {

    @Test
    @Sql("/db/base_booking.sql")
    void shouldGetBookingsTest() {
        LocalDate today = LocalDate.now();
        webTestClient()
                .get()
                .uri("/api/bookings/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()

                .json("{\"id\":1,\"clientId\":1,\"roomId\":1,\"startDate\":\"" + today.plusDays(10) + "\",\"endDate\":\"" + today.plusDays(13) + "\"}");
    }

    @Test
    void shouldGetBookingsNotFoundTest() {
        webTestClient()
                .get()
                .uri("/api/bookings/1")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

}
