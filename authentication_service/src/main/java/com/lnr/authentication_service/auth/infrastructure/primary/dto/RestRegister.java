package com.lnr.authentication_service.auth.infrastructure.primary.dto;

import com.lnr.authentication_service.auth.domain.account.vo.UserEmail;
import com.lnr.authentication_service.auth.domain.account.vo.UserLastSeen;
import com.lnr.authentication_service.auth.domain.account.vo.UserPassword;
import com.lnr.authentication_service.user.domain.profile.vo.*;
import com.lnr.authentication_service.user.domain.user.aggrigate.User;
import org.jilt.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

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
) {


    public static User toDomain(RestRegister dto, PasswordEncoder encoder) {
        String hashed = encoder.encode(dto.password()); // infrastructure hashes
        UserPassword passwordVO = new UserPassword(hashed);

        UserImage userImage = null;
        if (dto.imageBase64() != null && dto.imageType() != null) {
            byte[] decoded = Base64.getDecoder().decode(dto.imageBase64());


            userImage = new UserImage(decoded, dto.imageType());
        }

        UserAddress userAddress = null;
        if (dto.street() != null && dto.city() != null && dto.zipCode() != null && dto.country() != null) {
            userAddress = new UserAddress(dto.street(), dto.city(), dto.zipCode(), dto.country());
        }

        UserPhoneNumber userPhoneNumber = null;
        if (dto.countryCode() != null && dto.phoneNumber() != null) {
            userPhoneNumber = new UserPhoneNumber(dto.countryCode(), dto.phoneNumber());
        }

        return new User(
                new UserPublicId(UUID.randomUUID()),
                new UserFirstName(dto.firstName()),
                new UserLastName(dto.lastName()),
                new UserEmail(dto.email()),
                passwordVO,
                new UserLastSeen(Instant.now()),
                userAddress,
                userPhoneNumber,
                userImage,
                null // dbId handled by persistence


        );
    }
}
