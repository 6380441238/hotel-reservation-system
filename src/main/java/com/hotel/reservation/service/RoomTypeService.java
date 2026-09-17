package com.hotel.reservation.service;

import com.hotel.reservation.dto.RoomTypeRequestDTO;
import com.hotel.reservation.dto.RoomTypeResponseDTO;
import com.hotel.reservation.entity.RoomType;
import com.hotel.reservation.exception.ResourceNotFoundException;
import com.hotel.reservation.mapper.RoomTypeMapper;
import com.hotel.reservation.repository.RoomTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    public RoomTypeResponseDTO createRoomType(
            RoomTypeRequestDTO dto) {

        RoomType roomType =
                RoomTypeMapper.toEntity(dto);

        RoomType savedRoomType =
                roomTypeRepository.save(roomType);

        return RoomTypeMapper.toResponseDTO(
                savedRoomType);
    }

    public List<RoomTypeResponseDTO> getAllRoomTypes() {

        return roomTypeRepository.findAll()
                .stream()
                .map(RoomTypeMapper::toResponseDTO)
                .toList();
    }

    public RoomTypeResponseDTO getRoomTypeById(Long id) {

        RoomType roomType =
                roomTypeRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Room type not found with id: "
                                                + id));

        return RoomTypeMapper.toResponseDTO(roomType);
    }

    public RoomTypeResponseDTO updateRoomType(
            Long id,
            RoomTypeRequestDTO dto) {

        RoomType roomType =
                roomTypeRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Room type not found with id: "
                                                + id));

        roomType.setTypeName(dto.getTypeName());
        roomType.setDescription(dto.getDescription());
        roomType.setBasePrice(dto.getBasePrice());

        RoomType updatedRoomType =
                roomTypeRepository.save(roomType);

        return RoomTypeMapper.toResponseDTO(
                updatedRoomType);
    }

    public void deleteRoomType(Long id) {

        RoomType roomType =
                roomTypeRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Room type not found with id: "
                                                + id));

        roomTypeRepository.delete(roomType);
    }
}