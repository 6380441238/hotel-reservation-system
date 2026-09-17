package com.hotel.reservation.controller;

import com.hotel.reservation.dto.RoomRequestDTO;
import com.hotel.reservation.dto.RoomResponseDTO;
import com.hotel.reservation.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "Create a new room")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Room created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid room data"),
            @ApiResponse(responseCode = "404", description = "Room type not found")
    })
    @PostMapping
    public ResponseEntity<RoomResponseDTO> createRoom(
            @Valid @RequestBody RoomRequestDTO dto) {

        RoomResponseDTO response =
                roomService.createRoom(dto);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Get all rooms")
    @ApiResponse(
            responseCode = "200",
            description = "Rooms retrieved successfully"
    )
    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> getAllRooms() {

        return ResponseEntity.ok(
                roomService.getAllRooms()
        );
    }

    @Operation(summary = "Get room by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room found"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> getRoomById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                roomService.getRoomById(id)
        );
    }

    @Operation(summary = "Update room")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid room data"),
            @ApiResponse(responseCode = "404", description = "Room or room type not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomRequestDTO dto) {

        return ResponseEntity.ok(
                roomService.updateRoom(id, dto)
        );
    }

    @Operation(summary = "Delete room")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Room deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(
            @PathVariable Long id) {

        roomService.deleteRoom(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add an amenity to a room")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Amenity added to room successfully"),
            @ApiResponse(responseCode = "404", description = "Room or amenity not found")
    })
    @PutMapping("/{roomId}/amenities/{amenityId}")
    public ResponseEntity<RoomResponseDTO> addAmenityToRoom(
            @PathVariable Long roomId,
            @PathVariable Long amenityId) {

        return ResponseEntity.ok(
                roomService.addAmenityToRoom(
                        roomId,
                        amenityId
                )
        );
    }
}