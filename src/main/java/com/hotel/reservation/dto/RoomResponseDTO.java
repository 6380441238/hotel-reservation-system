package com.hotel.reservation.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoomResponseDTO {

    private Long roomId;
    private String roomNumber;
    private Integer floor;
    private String status;

    private Long roomTypeId;
    private String roomTypeName;

    private List<AmenityResponseDTO> amenities;
}