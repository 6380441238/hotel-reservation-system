package com.hotel.reservation.repository;

import com.hotel.reservation.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {

}