package com.hotel.reservation.mapper;

import com.hotel.reservation.dto.AmenityResponseDTO;
import com.hotel.reservation.dto.RoomRequestDTO;
import com.hotel.reservation.dto.RoomResponseDTO;
import com.hotel.reservation.entity.Amenity;
import com.hotel.reservation.entity.Room;
import com.hotel.reservation.entity.RoomType;

import java.util.List;

public class RoomMapper {

    public static Room toEntity(
            RoomRequestDTO dto,
            RoomType roomType) {

        Room room = new Room();
        room.setRoomNumber(dto.getRoomNumber());
        room.setFloor(dto.getFloor());
        room.setStatus(dto.getStatus());
        room.setRoomType(roomType);

        return room;
    }

    public static RoomResponseDTO toResponseDTO(Room room) {

        RoomResponseDTO dto = new RoomResponseDTO();

        dto.setRoomId(room.getRoomId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setFloor(room.getFloor());
        dto.setStatus(room.getStatus());

        if (room.getRoomType() != null) {

            dto.setRoomTypeId(
                    room.getRoomType().getRoomTypeId()
            );

            dto.setRoomTypeName(
                    room.getRoomType().getTypeName()
            );
        }

        // Convert Room Amenities to Response DTOs
        if (room.getAmenities() != null) {

            List<AmenityResponseDTO> amenities =
                    room.getAmenities()
                            .stream()
                            .map(RoomMapper::toAmenityResponseDTO)
                            .toList();

            dto.setAmenities(amenities);
        }

        return dto;
    }

    private static AmenityResponseDTO toAmenityResponseDTO(
            Amenity amenity) {

        AmenityResponseDTO dto =
                new AmenityResponseDTO();

        dto.setAmenityId(
                amenity.getAmenityId()
        );

        dto.setName(
                amenity.getName()
        );

        dto.setDescription(
                amenity.getDescription()
        );

        return dto;
    }
}