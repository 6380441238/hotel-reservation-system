package com.hotel.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AmenityRequestDTO {

    @NotBlank(message = "Amenity name is required")
    private String name;

    private String description;
}