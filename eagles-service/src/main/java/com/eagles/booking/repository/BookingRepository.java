package com.eagles.booking.repository;

import com.eagles.booking.domain.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface BookingRepository extends PagingAndSortingRepository<Booking, Long> {

    @Query(value = "select b from Booking b where b.roomId = ?1 and (" +
            "b.startDate between ?2 and ?3 or " +
            "b.endDate between ?2 and ?3 or " +
            "?2 between b.startDate and b.endDate or " +
            "b.startDate = ?2 or " +
            "b.endDate = ?3 or " +
            "b.startDate = ?3 or " +
            "b.endDate = ?2)", nativeQuery = false)
    Collection<Booking> findByRoomIdAndConflictDate(Long roomId, LocalDate beginStartDate, LocalDate finishStartDate);

}
