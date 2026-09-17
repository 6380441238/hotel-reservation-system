package com.hotel.reservation.controller;

import com.hotel.reservation.dto.AmenityRequestDTO;
import com.hotel.reservation.dto.AmenityResponseDTO;
import com.hotel.reservation.service.AmenityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amenities")
public class AmenityController {

    private final AmenityService amenityService;

    public AmenityController(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    @Operation(summary = "Create a new amenity")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Amenity created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid amenity data")
    })
    @PostMapping
    public ResponseEntity<AmenityResponseDTO> createAmenity(
            @Valid @RequestBody AmenityRequestDTO dto) {

        AmenityResponseDTO response =
                amenityService.createAmenity(dto);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Get all amenities")
    @ApiResponse(
            responseCode = "200",
            description = "Amenities retrieved successfully"
    )
    @GetMapping
    public ResponseEntity<List<AmenityResponseDTO>> getAllAmenities() {

        return ResponseEntity.ok(
                amenityService.getAllAmenities()
        );
    }

    @Operation(summary = "Get amenity by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Amenity found"),
            @ApiResponse(responseCode = "404", description = "Amenity not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AmenityResponseDTO> getAmenityById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                amenityService.getAmenityById(id)
        );
    }

    @Operation(summary = "Update amenity")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Amenity updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid amenity data"),
            @ApiResponse(responseCode = "404", description = "Amenity not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AmenityResponseDTO> updateAmenity(
            @PathVariable Long id,
            @Valid @RequestBody AmenityRequestDTO dto) {

        return ResponseEntity.ok(
                amenityService.updateAmenity(id, dto)
        );
    }

    @Operation(summary = "Delete amenity")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Amenity deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Amenity not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmenity(
            @PathVariable Long id) {

        amenityService.deleteAmenity(id);

        return ResponseEntity.noContent().build();
    }
}