package com.github.zambrinn.mvcproject.DTOs;

public record AddressResponse(
        String street,
        String number,
        String city,
        String state
) {
}
