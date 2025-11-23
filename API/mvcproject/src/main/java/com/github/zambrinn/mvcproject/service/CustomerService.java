package com.github.zambrinn.mvcproject.service;

import com.github.zambrinn.mvcproject.DTOs.CustomerRequest;
import com.github.zambrinn.mvcproject.DTOs.CustomerResponse;
import com.github.zambrinn.mvcproject.model.Customer;
import com.github.zambrinn.mvcproject.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = Customer.builder()
                .name(request.name())
                .email(request.email())
                .passwordHash(request.passwordHash())
                .cpf(request.cpf())
                .phone(request.phone())
                .createdAt(LocalDateTime.now())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        return convertToDTO(savedCustomer);
    }

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public CustomerResponse updateCustomer(UUID id, CustomerRequest request) {
        Customer foundCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer with id " + id + " wasn't found"));

        foundCustomer.setCpf(request.cpf());
        foundCustomer.setPasswordHash(request.passwordHash());
        foundCustomer.setName(request.name());
        foundCustomer.setEmail(request.email());
        foundCustomer.setPhone(request.phone());

        Customer updatedCustomer = customerRepository.save(foundCustomer);
        return convertToDTO(updatedCustomer);
    }

    public void deleteCustomer(UUID id) {
        Customer foundCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer with id " + id + " wasn't found"));

        customerRepository.delete(foundCustomer);
    }


    public CustomerResponse convertToDTO(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getEmail(),
                customer.getCreatedAt()
        );
    }

}

