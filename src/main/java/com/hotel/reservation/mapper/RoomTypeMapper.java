package com.hotel.reservation.mapper;

import com.hotel.reservation.dto.RoomTypeRequestDTO;
import com.hotel.reservation.dto.RoomTypeResponseDTO;
import com.hotel.reservation.entity.RoomType;

public class RoomTypeMapper {

    public static RoomType toEntity(RoomTypeRequestDTO dto) {

        RoomType roomType = new RoomType();

        roomType.setTypeName(dto.getTypeName());
        roomType.setDescription(dto.getDescription());
        roomType.setBasePrice(dto.getBasePrice());

        return roomType;
    }

    public static RoomTypeResponseDTO toResponseDTO(RoomType roomType) {

        RoomTypeResponseDTO dto = new RoomTypeResponseDTO();

        dto.setRoomTypeId(roomType.getRoomTypeId());
        dto.setTypeName(roomType.getTypeName());
        dto.setDescription(roomType.getDescription());
        dto.setBasePrice(roomType.getBasePrice());

        return dto;
    }
}