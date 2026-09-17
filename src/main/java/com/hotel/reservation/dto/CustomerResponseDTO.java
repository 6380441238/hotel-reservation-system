package com.hotel.reservation.dto;

import lombok.Data;

@Data
public class CustomerResponseDTO {

    private Long customerId;
    private String name;
    private String email;
    private String phone;
}