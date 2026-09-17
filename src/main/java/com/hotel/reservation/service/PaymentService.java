package com.hotel.reservation.service;

import com.hotel.reservation.dto.PaymentRequestDTO;
import com.hotel.reservation.dto.PaymentResponseDTO;
import com.hotel.reservation.entity.Payment;
import com.hotel.reservation.entity.Reservation;
import com.hotel.reservation.exception.BusinessException;
import com.hotel.reservation.exception.ResourceNotFoundException;
import com.hotel.reservation.mapper.PaymentMapper;
import com.hotel.reservation.repository.PaymentRepository;
import com.hotel.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            ReservationRepository reservationRepository) {

        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
    }

    // Create Payment
    @Transactional
    public PaymentResponseDTO createPayment(
            PaymentRequestDTO dto) {

        // Check reservation exists
        Reservation reservation =
                reservationRepository.findById(dto.getReservationId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Reservation not found with id: "
                                                + dto.getReservationId()));

        // Check reservation status
        if (!"CONFIRMED".equals(reservation.getStatus())) {
            throw new BusinessException(
                    "Payment can be made only for a confirmed reservation");
        }

        // Check duplicate payment
        boolean paymentExists =
                paymentRepository.existsByReservationReservationId(
                        dto.getReservationId());

        if (paymentExists) {
            throw new BusinessException(
                    "Payment already exists for reservation id: "
                            + dto.getReservationId());
        }

        // Check payment amount
        BigDecimal reservationAmount =
                reservation.getTotalAmount();

        if (dto.getAmount().compareTo(reservationAmount) != 0) {
            throw new BusinessException(
                    "Payment amount must be equal to reservation total amount: "
                            + reservationAmount);
        }

        // Convert DTO to Entity
        Payment payment =
                PaymentMapper.toEntity(
                        dto,
                        reservation);

        // Set payment details
        payment.setPaymentStatus("SUCCESS");
        payment.setPaymentDate(LocalDateTime.now());

        // Save payment
        Payment savedPayment =
                paymentRepository.save(payment);

        // Convert Entity to Response DTO
        return PaymentMapper.toResponseDTO(
                savedPayment);
    }

    // Get Payment By ID
    public PaymentResponseDTO getPaymentById(Long id) {

        Payment payment =
                paymentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payment not found with id: "
                                                + id));

        return PaymentMapper.toResponseDTO(payment);
    }
}