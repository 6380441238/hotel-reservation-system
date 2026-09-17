package com.hotel.reservation.service;

import com.hotel.reservation.dto.AmenityRequestDTO;
import com.hotel.reservation.dto.AmenityResponseDTO;
import com.hotel.reservation.entity.Amenity;
import com.hotel.reservation.exception.ResourceNotFoundException;
import com.hotel.reservation.mapper.AmenityMapper;
import com.hotel.reservation.repository.AmenityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmenityService {

    private final AmenityRepository amenityRepository;

    public AmenityService(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    public AmenityResponseDTO createAmenity(
            AmenityRequestDTO dto) {

        Amenity amenity =
                AmenityMapper.toEntity(dto);

        Amenity savedAmenity =
                amenityRepository.save(amenity);

        return AmenityMapper.toResponseDTO(savedAmenity);
    }

    public List<AmenityResponseDTO> getAllAmenities() {

        return amenityRepository.findAll()
                .stream()
                .map(AmenityMapper::toResponseDTO)
                .toList();
    }

    public AmenityResponseDTO getAmenityById(Long id) {

        Amenity amenity =
                amenityRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Amenity not found with id: "
                                                + id));

        return AmenityMapper.toResponseDTO(amenity);
    }

    public AmenityResponseDTO updateAmenity(
            Long id,
            AmenityRequestDTO dto) {

        Amenity amenity =
                amenityRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Amenity not found with id: "
                                                + id));

        amenity.setName(dto.getName());
        amenity.setDescription(dto.getDescription());

        Amenity updatedAmenity =
                amenityRepository.save(amenity);

        return AmenityMapper.toResponseDTO(updatedAmenity);
    }

    public void deleteAmenity(Long id) {

        Amenity amenity =
                amenityRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Amenity not found with id: "
                                                + id));

        amenityRepository.delete(amenity);
    }
}