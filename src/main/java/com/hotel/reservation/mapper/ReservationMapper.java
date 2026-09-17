package com.hotel.reservation.mapper;

import com.hotel.reservation.dto.ReservationRequestDTO;
import com.hotel.reservation.dto.ReservationResponseDTO;
import com.hotel.reservation.entity.Customer;
import com.hotel.reservation.entity.Reservation;
import com.hotel.reservation.entity.Room;

public class ReservationMapper {

    public static Reservation toEntity(
            ReservationRequestDTO dto,
            Customer customer,
            Room room) {

        Reservation reservation = new Reservation();

        reservation.setCustomer(customer);
        reservation.setRoom(room);
        reservation.setCheckInDate(dto.getCheckInDate());
        reservation.setCheckOutDate(dto.getCheckOutDate());

        return reservation;
    }

    public static ReservationResponseDTO toResponseDTO(
            Reservation reservation) {

        ReservationResponseDTO dto = new ReservationResponseDTO();

        dto.setReservationId(reservation.getReservationId());

        if (reservation.getCustomer() != null) {
            dto.setCustomerId(
                    reservation.getCustomer().getCustomerId()
            );
            dto.setCustomerName(
                    reservation.getCustomer().getName()
            );
        }

        if (reservation.getRoom() != null) {
            dto.setRoomId(
                    reservation.getRoom().getRoomId()
            );
            dto.setRoomNumber(
                    reservation.getRoom().getRoomNumber()
            );
        }

        dto.setCheckInDate(reservation.getCheckInDate());
        dto.setCheckOutDate(reservation.getCheckOutDate());
        dto.setStatus(reservation.getStatus());
        dto.setTotalAmount(reservation.getTotalAmount());

        return dto;
    }
}