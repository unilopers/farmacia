package com.github.zambrinn.mvcproject.service;

import com.github.zambrinn.mvcproject.DTOs.AddressRequest;
import com.github.zambrinn.mvcproject.DTOs.AddressResponse;
import com.github.zambrinn.mvcproject.model.Address;
import com.github.zambrinn.mvcproject.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public AddressResponse createAddress(AddressRequest request) {
        Address address = Address.builder()
                .state(request.state())
                .city(request.city())
                .street(request.street())
                .number(request.number())
                .build();

        Address newAddress = addressRepository.save(address);

        return convertToDTO(newAddress);
    }

    public List<AddressResponse> getAllAddress() {
        return addressRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AddressResponse updateAddress(UUID id, AddressRequest request) {
        Address foundAddress = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Couldn't find any address with id" + id));

        foundAddress.setCity(request.city());
        foundAddress.setNumber(request.number());
        foundAddress.setState(request.state());
        foundAddress.setStreet(request.street());

        Address updatedAddress = addressRepository.save(foundAddress);

        return convertToDTO(updatedAddress);
    }

    public void deleteAddress(UUID id) {
        Address foundAddress = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Couldn't find any address with id" + id));

        addressRepository.delete(foundAddress);
    }

    public AddressResponse convertToDTO(Address address) {
        return new AddressResponse(
                address.getStreet(),
                address.getCity(),
                address.getNumber(),
                address.getState()
        );
    }
}
