package com.hotel.reservation.mapper;

import com.hotel.reservation.dto.CustomerRequestDTO;
import com.hotel.reservation.dto.CustomerResponseDTO;
import com.hotel.reservation.entity.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerRequestDTO dto) {

        Customer customer = new Customer();

        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());

        return customer;
    }

    public static CustomerResponseDTO toResponseDTO(Customer customer) {

        CustomerResponseDTO dto = new CustomerResponseDTO();

        dto.setCustomerId(customer.getCustomerId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());

        return dto;
    }
}