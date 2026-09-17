package com.hotel.reservation.mapper;

import com.hotel.reservation.dto.AmenityRequestDTO;
import com.hotel.reservation.dto.AmenityResponseDTO;
import com.hotel.reservation.entity.Amenity;

public class AmenityMapper {

    public static Amenity toEntity(AmenityRequestDTO dto) {

        Amenity amenity = new Amenity();

        amenity.setName(dto.getName());
        amenity.setDescription(dto.getDescription());

        return amenity;
    }

    public static AmenityResponseDTO toResponseDTO(Amenity amenity) {

        AmenityResponseDTO dto = new AmenityResponseDTO();

        dto.setAmenityId(amenity.getAmenityId());
        dto.setName(amenity.getName());
        dto.setDescription(amenity.getDescription());

        return dto;
    }
}