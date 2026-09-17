package com.hotel.reservation.service;

import com.hotel.reservation.dto.ReservationRequestDTO;
import com.hotel.reservation.dto.ReservationResponseDTO;
import com.hotel.reservation.entity.Customer;
import com.hotel.reservation.entity.Reservation;
import com.hotel.reservation.entity.Room;
import com.hotel.reservation.exception.BusinessException;
import com.hotel.reservation.exception.ResourceNotFoundException;
import com.hotel.reservation.mapper.ReservationMapper;
import com.hotel.reservation.repository.CustomerRepository;
import com.hotel.reservation.repository.ReservationRepository;
import com.hotel.reservation.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            CustomerRepository customerRepository,
            RoomRepository roomRepository) {

        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
    }

    // Create Reservation
    @Transactional
    public ReservationResponseDTO createReservation(
            ReservationRequestDTO dto) {

        Customer customer =
                customerRepository.findById(dto.getCustomerId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Customer not found with id: "
                                                + dto.getCustomerId()));

        Room room =
                roomRepository.findById(dto.getRoomId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Room not found with id: "
                                                + dto.getRoomId()));

        validateDates(
                dto.getCheckInDate(),
                dto.getCheckOutDate());

        boolean available =
                isRoomAvailable(
                        dto.getRoomId(),
                        dto.getCheckInDate(),
                        dto.getCheckOutDate());

        if (!available) {
            throw new BusinessException(
                    "Room is not available for the selected dates");
        }

        Reservation reservation =
                ReservationMapper.toEntity(
                        dto,
                        customer,
                        room);

        reservation.setStatus("CONFIRMED");

        BigDecimal totalAmount =
                calculateTotalAmount(
                        room,
                        dto.getCheckInDate(),
                        dto.getCheckOutDate());

        reservation.setTotalAmount(totalAmount);

        Reservation savedReservation =
                reservationRepository.save(reservation);

        return ReservationMapper.toResponseDTO(
                savedReservation);
    }

    // Check Room Availability
    public boolean isRoomAvailable(
            Long roomId,
            LocalDate checkInDate,
            LocalDate checkOutDate) {

        validateDates(checkInDate, checkOutDate);

        return reservationRepository
                .findOverlappingReservations(
                        roomId,
                        checkInDate,
                        checkOutDate)
                .isEmpty();
    }

    // Update Reservation
    @Transactional
    public ReservationResponseDTO updateReservation(
            Long reservationId,
            ReservationRequestDTO dto) {

        Reservation reservation =
                reservationRepository.findByReservationIdAndStatus(
                        reservationId,
                        "CONFIRMED");

        if (reservation == null) {
            throw new ResourceNotFoundException(
                    "Confirmed reservation not found with id: "
                            + reservationId);
        }

        Customer customer =
                customerRepository.findById(dto.getCustomerId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Customer not found with id: "
                                                + dto.getCustomerId()));

        Room room =
                roomRepository.findById(dto.getRoomId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Room not found with id: "
                                                + dto.getRoomId()));

        validateDates(
                dto.getCheckInDate(),
                dto.getCheckOutDate());

        boolean available =
                reservationRepository
                        .findOverlappingReservations(
                                dto.getRoomId(),
                                dto.getCheckInDate(),
                                dto.getCheckOutDate())
                        .stream()
                        .noneMatch(existing ->
                                !existing.getReservationId()
                                        .equals(reservationId));

        if (!available) {
            throw new BusinessException(
                    "Room is not available for the selected dates");
        }

        reservation.setCustomer(customer);
        reservation.setRoom(room);
        reservation.setCheckInDate(dto.getCheckInDate());
        reservation.setCheckOutDate(dto.getCheckOutDate());

        BigDecimal totalAmount =
                calculateTotalAmount(
                        room,
                        dto.getCheckInDate(),
                        dto.getCheckOutDate());

        reservation.setTotalAmount(totalAmount);

        Reservation updatedReservation =
                reservationRepository.save(reservation);

        return ReservationMapper.toResponseDTO(
                updatedReservation);
    }

    // Cancel Reservation
    @Transactional
    public void cancelReservation(Long reservationId) {

        Reservation reservation =
                reservationRepository.findByReservationIdAndStatus(
                        reservationId,
                        "CONFIRMED");

        if (reservation == null) {
            throw new ResourceNotFoundException(
                    "Confirmed reservation not found with id: "
                            + reservationId);
        }

        reservation.setStatus("CANCELLED");

        reservationRepository.save(reservation);
    }

    // Get Customer Reservation History
    public List<ReservationResponseDTO> getCustomerHistory(
            Long customerId) {

        customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: "
                                        + customerId));

        return reservationRepository
                .findByCustomerCustomerId(customerId)
                .stream()
                .map(ReservationMapper::toResponseDTO)
                .toList();
    }

    // Validate Dates
    private void validateDates(
            LocalDate checkInDate,
            LocalDate checkOutDate) {

        if (!checkInDate.isBefore(checkOutDate)) {
            throw new BusinessException(
                    "Check-in date must be before check-out date");
        }
    }

    // Calculate Total Amount
    private BigDecimal calculateTotalAmount(
            Room room,
            LocalDate checkInDate,
            LocalDate checkOutDate) {

        long numberOfNights =
                ChronoUnit.DAYS.between(
                        checkInDate,
                        checkOutDate);

        return room.getRoomType()
                .getBasePrice()
                .multiply(
                        BigDecimal.valueOf(numberOfNights));
    }
}