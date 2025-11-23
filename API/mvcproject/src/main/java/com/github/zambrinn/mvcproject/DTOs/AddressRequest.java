package com.github.zambrinn.mvcproject.DTOs;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
        @NotBlank(message = "The address must have a street")
        String street,

        @NotBlank(message = "The address must have a number")
        String number,

        @NotBlank(message = "The address must have a city")
        String city,

        @NotBlank(message = "The address must have a state")
        String state

) {
}
