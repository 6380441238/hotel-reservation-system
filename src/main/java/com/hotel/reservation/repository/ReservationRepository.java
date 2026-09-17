package com.hotel.reservation.repository;

import com.hotel.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository
        extends JpaRepository<Reservation, Long> {

    // Find reservations that overlap with the selected dates
    @Query("""
            SELECT r
            FROM Reservation r
            WHERE r.room.roomId = :roomId
            AND r.status = 'CONFIRMED'
            AND r.checkInDate < :checkOutDate
            AND r.checkOutDate > :checkInDate
            """)
    List<Reservation> findOverlappingReservations(
            @Param("roomId") Long roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate);

    // Find reservation by ID and status
    Reservation findByReservationIdAndStatus(
            Long reservationId,
            String status);

    // Find all reservations of a customer
    List<Reservation> findByCustomerCustomerId(
            Long customerId);
}