package com.hotel.reservation.controller;

import com.hotel.reservation.dto.ReservationRequestDTO;
import com.hotel.reservation.dto.ReservationResponseDTO;
import com.hotel.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(
            ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Create a new reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reservation created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid dates or room not available"),
            @ApiResponse(responseCode = "404", description = "Customer or room not found")
    })
    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(
            @Valid @RequestBody ReservationRequestDTO dto) {

        ReservationResponseDTO response =
                reservationService.createReservation(dto);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Check room availability")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room availability checked successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid date range")
    })
    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkRoomAvailability(
            @RequestParam Long roomId,
            @RequestParam String checkInDate,
            @RequestParam String checkOutDate) {

        LocalDate checkIn =
                LocalDate.parse(checkInDate);

        LocalDate checkOut =
                LocalDate.parse(checkOutDate);

        boolean available =
                reservationService.isRoomAvailable(
                        roomId,
                        checkIn,
                        checkOut
                );

        return ResponseEntity.ok(available);
    }

    @Operation(summary = "Update an existing reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid dates or room not available"),
            @ApiResponse(responseCode = "404", description = "Reservation, customer or room not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> updateReservation(
            @PathVariable Long id,
            @Valid @RequestBody ReservationRequestDTO dto) {

        ReservationResponseDTO response =
                reservationService.updateReservation(
                        id,
                        dto
                );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cancel a reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Confirmed reservation not found")
    })
    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelReservation(
            @PathVariable Long id) {

        reservationService.cancelReservation(id);

        return ResponseEntity.ok(
                "Reservation cancelled successfully"
        );
    }

    @Operation(summary = "Get customer reservation history")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer reservation history retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ReservationResponseDTO>> getCustomerHistory(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                reservationService.getCustomerHistory(
                        customerId
                )
        );
    }
}