package com.hotel.reservation.controller;

import com.hotel.reservation.dto.RoomTypeRequestDTO;
import com.hotel.reservation.dto.RoomTypeResponseDTO;
import com.hotel.reservation.service.RoomTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-types")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    public RoomTypeController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @Operation(summary = "Create a new room type")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Room type created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid room type data")
    })
    @PostMapping
    public ResponseEntity<RoomTypeResponseDTO> createRoomType(
            @Valid @RequestBody RoomTypeRequestDTO dto) {

        RoomTypeResponseDTO response =
                roomTypeService.createRoomType(dto);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Get all room types")
    @ApiResponse(responseCode = "200", description = "Room types retrieved successfully")
    @GetMapping
    public ResponseEntity<List<RoomTypeResponseDTO>> getAllRoomTypes() {

        return ResponseEntity.ok(
                roomTypeService.getAllRoomTypes()
        );
    }

    @Operation(summary = "Get room type by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room type found"),
            @ApiResponse(responseCode = "404", description = "Room type not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeResponseDTO> getRoomTypeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                roomTypeService.getRoomTypeById(id)
        );
    }

    @Operation(summary = "Update room type")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room type updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid room type data"),
            @ApiResponse(responseCode = "404", description = "Room type not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RoomTypeResponseDTO> updateRoomType(
            @PathVariable Long id,
            @Valid @RequestBody RoomTypeRequestDTO dto) {

        return ResponseEntity.ok(
                roomTypeService.updateRoomType(id, dto)
        );
    }

    @Operation(summary = "Delete room type")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Room type deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Room type not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomType(
            @PathVariable Long id) {

        roomTypeService.deleteRoomType(id);

        return ResponseEntity.noContent().build();
    }
}