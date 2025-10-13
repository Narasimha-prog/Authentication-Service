package com.lnr.authentication_service.auth.infrastructure.primary;

import org.jilt.Builder;

@Builder
public record RestRegister(
        String firstName,
        String lastName,
        String email,
        String password,
        // Split address fields to map into UserAddress VO
        String street,
        String city,
        String zipCode,
        String country,
        String countryCode,
        Long phoneNumber,
        String imageBase64, // Base64-encoded image
        String imageType    // e.g. "png", "jpeg"
) {}
