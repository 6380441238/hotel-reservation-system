package com.hotel.reservation.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReservationResponseDTO {

    private Long reservationId;

    private Long customerId;
    private String customerName;

    private Long roomId;
    private String roomNumber;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private String status;
    private BigDecimal totalAmount;
}