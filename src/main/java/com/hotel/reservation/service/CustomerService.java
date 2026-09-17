package com.hotel.reservation.service;

import com.hotel.reservation.dto.CustomerRequestDTO;
import com.hotel.reservation.dto.CustomerResponseDTO;
import com.hotel.reservation.entity.Customer;
import com.hotel.reservation.exception.ResourceNotFoundException;
import com.hotel.reservation.mapper.CustomerMapper;
import com.hotel.reservation.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto) {

        Customer customer = CustomerMapper.toEntity(dto);

        Customer savedCustomer =
                customerRepository.save(customer);

        return CustomerMapper.toResponseDTO(savedCustomer);
    }

    public List<CustomerResponseDTO> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::toResponseDTO)
                .toList();
    }

    public CustomerResponseDTO getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + id));

        return CustomerMapper.toResponseDTO(customer);
    }

    public CustomerResponseDTO updateCustomer(
            Long id,
            CustomerRequestDTO dto) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + id));

        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());

        Customer updatedCustomer =
                customerRepository.save(customer);

        return CustomerMapper.toResponseDTO(updatedCustomer);
    }

    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + id));

        customerRepository.delete(customer);
    }
}