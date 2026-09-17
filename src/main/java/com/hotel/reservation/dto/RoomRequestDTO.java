package com.hotel.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomRequestDTO {

    @NotBlank(message = "Room number is required")
    private String roomNumber;

    @NotNull(message = "Floor is required")
    private Integer floor;

    @NotBlank(message = "Room status is required")
    private String status;

    @NotNull(message = "Room type is required")
    private Long roomTypeId;
}