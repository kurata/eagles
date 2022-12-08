package com.eagles.booking;

import com.eagles.booking.api.BookingApiSchema;
import com.eagles.utils.AbstractApplicationTestStart;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

class PostBookingTest extends AbstractApplicationTestStart {

    @Test
    void shouldPostBookingTest() {
        LocalDate today = LocalDate.now();
        webTestClient()
                .post()
                .uri("/api/bookings")
                .bodyValue(new BookingApiSchema(null, 1L, 1L, today.plusDays(10), today.plusDays(12)))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .json("{\"id\":1,\"clientId\":1,\"roomId\":1,\"startDate\":\"" + today.plusDays(10) + "\",\"endDate\":\"" + today.plusDays(12) + "\"}");
    }

    @Test
    void shouldPostBookingWithTodayStartDateTest() {
        LocalDate today = LocalDate.now();
        webTestClient()
                .post()
                .uri("/api/bookings")
                .bodyValue(new BookingApiSchema(null, 1L, 1L, today, today.plusDays(2)))
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .json("{" +
                        "  \"httpMethod\": \"POST\"," +
                        "  \"path\": \"/api/bookings\"," +
                        "  \"message\": \"The booking need start after today.\"," +
                        "  \"messageKey\": \"booking.need_start_after_today\"," +
                        "  \"details\": []," +
                        "  \"throwableClassName\": \"HttpBusinessException\"" +
                        "}");
    }

    @Test
    void shouldPostBookingWithPeriodMoreThanThreeDaysTest() {
        LocalDate today = LocalDate.now();
        webTestClient()
                .post()
                .uri("/api/bookings")
                .bodyValue(new BookingApiSchema(null, 1L, 1L, today.plusDays(10), today.plusDays(15)))
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .json("{" +
                        "  \"httpMethod\": \"POST\"," +
                        "  \"path\": \"/api/bookings\"," +
                        "  \"message\": \"It is not allowed to book more than three days.\"," +
                        "  \"messageKey\": \"booking.more_than_three_days_long\"," +
                        "  \"details\": []," +
                        "  \"throwableClassName\": \"HttpBusinessException\"" +
                        "}");
    }

    @Test
    void shouldPostBookingWithStartDateAfterThirtyDaysTest() {
        LocalDate today = LocalDate.now();
        webTestClient()
                .post()
                .uri("/api/bookings")
                .bodyValue(new BookingApiSchema(null, 1L, 1L, today.plusDays(36), today.plusDays(39)))
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .json("{" +
                        "  \"httpMethod\": \"POST\"," +
                        "  \"path\": \"/api/bookings\"," +
                        "  \"message\": \"Reservations older than thirty days are not allowed.\"," +
                        "  \"messageKey\": \"booking.more_than_thirty_days_advanced\"," +
                        "  \"details\": []," +
                        "  \"throwableClassName\": \"HttpBusinessException\"" +
                        "}");
    }

    @Test
    @Sql("/db/base_booking.sql")
    void shouldPostBookingWithSamePeriodTest() {
        LocalDate today = LocalDate.now();
        webTestClient()
                .post()
                .uri("/api/bookings")
                .bodyValue(new BookingApiSchema(null, 1L, 1L, today.plusDays(10), today.plusDays(13)))
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }

    @Test
    @Sql("/db/base_booking.sql")
    void shouldPostBookingWithEndConflictTest() {
        LocalDate today = LocalDate.now();
        webTestClient()
                .post()
                .uri("/api/bookings")
                .bodyValue(new BookingApiSchema(null, 1L, 1L, today.plusDays(12), today.plusDays(15)))
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody();
    }

}
