package com.lnr.authentication_service.user.infrastrature.primary.dto;


import com.lnr.authentication_service.user.domain.user.aggrigate.User;

import java.util.UUID;

public record RestUser(
        UUID publicId,
        String firstName,
        String lastName,
        String email,
        String street,
        String city,
        String zipCode,
        String country,
        String countryCode,
        Long phoneNumber
        // intentionally no password or dbId
) {

    public static RestUser fromDomain(User user) {
        return new RestUser(
                user.getPublicId().value(),
                user.getFirstName().value(),
                user.getLastName().value(),
                user.getUserEmail().value(),
                user.getUserAddress() != null ? user.getUserAddress().street() : null,
                user.getUserAddress() != null ? user.getUserAddress().city() : null,
                user.getUserAddress() != null ? user.getUserAddress().zipCode() : null,
                user.getUserAddress() != null ? user.getUserAddress().country() : null,
                user.getUserPhoneNumber() != null ? user.getUserPhoneNumber().countryCode() : null,
                user.getUserPhoneNumber() != null ? user.getUserPhoneNumber().number() : null
        );
    }
}