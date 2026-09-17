package com.hotel.reservation.repository;

import com.hotel.reservation.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    // Check whether a payment already exists for a reservation
    boolean existsByReservationReservationId(Long reservationId);
}