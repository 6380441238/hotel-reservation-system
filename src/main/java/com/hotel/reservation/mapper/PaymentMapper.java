package com.hotel.reservation.mapper;

import com.hotel.reservation.dto.PaymentRequestDTO;
import com.hotel.reservation.dto.PaymentResponseDTO;
import com.hotel.reservation.entity.Payment;
import com.hotel.reservation.entity.Reservation;

public class PaymentMapper {

    public static Payment toEntity(
            PaymentRequestDTO dto,
            Reservation reservation) {

        Payment payment = new Payment();

        payment.setReservation(reservation);
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());

        return payment;
    }

    public static PaymentResponseDTO toResponseDTO(Payment payment) {

        PaymentResponseDTO dto = new PaymentResponseDTO();

        dto.setPaymentId(payment.getPaymentId());

        if (payment.getReservation() != null) {
            dto.setReservationId(
                    payment.getReservation().getReservationId()
            );
        }

        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setPaymentDate(payment.getPaymentDate());

        return dto;
    }
}