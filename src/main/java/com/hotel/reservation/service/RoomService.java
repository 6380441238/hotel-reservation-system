package com.hotel.reservation.service;

import com.hotel.reservation.dto.RoomRequestDTO;
import com.hotel.reservation.dto.RoomResponseDTO;
import com.hotel.reservation.entity.Amenity;
import com.hotel.reservation.entity.Room;
import com.hotel.reservation.entity.RoomType;
import com.hotel.reservation.exception.ResourceNotFoundException;
import com.hotel.reservation.mapper.RoomMapper;
import com.hotel.reservation.repository.AmenityRepository;
import com.hotel.reservation.repository.RoomRepository;
import com.hotel.reservation.repository.RoomTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final AmenityRepository amenityRepository;

    public RoomService(
            RoomRepository roomRepository,
            RoomTypeRepository roomTypeRepository,
            AmenityRepository amenityRepository) {

        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.amenityRepository = amenityRepository;
    }

    // Create Room
    public RoomResponseDTO createRoom(RoomRequestDTO dto) {

        RoomType roomType =
                roomTypeRepository.findById(dto.getRoomTypeId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Room type not found with id: "
                                                + dto.getRoomTypeId()));

        Room room = RoomMapper.toEntity(dto, roomType);

        Room savedRoom = roomRepository.save(room);

        return RoomMapper.toResponseDTO(savedRoom);
    }

    // Get All Rooms
    @Transactional(readOnly = true)
    public List<RoomResponseDTO> getAllRooms() {

        return roomRepository.findAll()
                .stream()
                .map(RoomMapper::toResponseDTO)
                .toList();
    }

    // Get Room By ID
    @Transactional(readOnly = true)
    public RoomResponseDTO getRoomById(Long id) {

        Room room =
                roomRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Room not found with id: " + id));

        return RoomMapper.toResponseDTO(room);
    }

    // Update Room
    public RoomResponseDTO updateRoom(
            Long id,
            RoomRequestDTO dto) {

        Room room =
                roomRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Room not found with id: " + id));

        RoomType roomType =
                roomTypeRepository.findById(dto.getRoomTypeId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Room type not found with id: "
                                                + dto.getRoomTypeId()));

        room.setRoomNumber(dto.getRoomNumber());
        room.setFloor(dto.getFloor());
        room.setStatus(dto.getStatus());
        room.setRoomType(roomType);

        Room updatedRoom =
                roomRepository.save(room);

        return RoomMapper.toResponseDTO(updatedRoom);
    }

    // Delete Room
    public void deleteRoom(Long id) {

        Room room =
                roomRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Room not found with id: " + id));

        roomRepository.delete(room);
    }

    // Add Amenity to Room
    @Transactional
    public RoomResponseDTO addAmenityToRoom(
            Long roomId,
            Long amenityId) {

        Room room =
                roomRepository.findById(roomId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Room not found with id: "
                                                + roomId));

        Amenity amenity =
                amenityRepository.findById(amenityId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Amenity not found with id: "
                                                + amenityId));

        if (room.getAmenities() == null) {
            room.setAmenities(new java.util.HashSet<>());
        }

        room.getAmenities().add(amenity);

        Room updatedRoom =
                roomRepository.save(room);

        return RoomMapper.toResponseDTO(updatedRoom);
    }
}