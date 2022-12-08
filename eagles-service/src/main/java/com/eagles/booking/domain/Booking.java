package com.eagles.booking.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Booking implements Serializable {

    @Id
    @SequenceGenerator(name = "booking_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_id_seq")
    private final Long id;

    private Long clientId;

    private Long roomId;

    private LocalDate startDate;

    private LocalDate endDate;

    public Booking(final Booking originalBooking) {
        this(originalBooking.getId());
        this.clientId = originalBooking.getClientId();
        this.roomId = originalBooking.getRoomId();
        this.startDate = originalBooking.getStartDate();
        this.endDate = originalBooking.getStartDate();
    }

    public Booking() {
        this((Long) null);
    }
}
