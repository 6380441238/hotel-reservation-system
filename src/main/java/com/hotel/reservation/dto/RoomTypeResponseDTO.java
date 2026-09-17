package com.hotel.reservation.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomTypeResponseDTO {

    private Long roomTypeId;
    private String typeName;
    private String description;
    private BigDecimal basePrice;
}