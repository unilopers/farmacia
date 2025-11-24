package com.github.zambrinn.mvcproject.controller;

import com.github.zambrinn.mvcproject.DTOs.AddressRequest;
import com.github.zambrinn.mvcproject.DTOs.AddressResponse;
import com.github.zambrinn.mvcproject.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/Address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress (@Valid @RequestBody AddressRequest request) {
        return ResponseEntity.ok(addressService.createAddress(request));
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAddress () {
        return ResponseEntity.ok(addressService.getAllAddress());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable UUID id, @Valid @RequestBody AddressRequest request) {
        return ResponseEntity.ok(addressService.updateAddress(id, request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        addressService.deleteAddress(id);
    }
}
