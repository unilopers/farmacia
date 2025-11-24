package com.github.zambrinn.mvcproject.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.zambrinn.mvcproject.DTOs.CustomerRequest;
import com.github.zambrinn.mvcproject.DTOs.CustomerResponse;
import com.github.zambrinn.mvcproject.model.Address;
import com.github.zambrinn.mvcproject.model.Customer;
import com.github.zambrinn.mvcproject.repository.AddressRepository;
import com.github.zambrinn.mvcproject.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

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

        if (request.address() != null) {
            Address address = Address.builder()
                    .street(request.address().street())
                    .number(request.address().number())
                    .city(request.address().city())
                    .state(request.address().state())
                    .customer(savedCustomer)
                    .build();
            
            addressRepository.save(address);
        }

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

        if (request.address() != null) {
            Address address = Address.builder()
                    .street(request.address().street())
                    .number(request.address().number())
                    .city(request.address().city())
                    .state(request.address().state())
                    .customer(updatedCustomer)
                    .build();
            
            addressRepository.save(address);
        }

        return convertToDTO(updatedCustomer);
    }

    public void deleteCustomer(UUID id) {
        Customer foundCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer with id " + id + " wasn't found"));

        customerRepository.delete(foundCustomer);
    }


    public CustomerResponse convertToDTO(Customer customer) {
        List<CustomerResponse.AddressDTO> addressDTOs = customer.getAddresses().stream()
                .map(address -> new CustomerResponse.AddressDTO(
                        address.getId(),
                        address.getStreet(),
                        address.getState(),
                        address.getCity(),
                        address.getNumber()
                ))
                .collect(Collectors.toList());

        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getCpf(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCreatedAt(),
                addressDTOs
        );
    }

}

