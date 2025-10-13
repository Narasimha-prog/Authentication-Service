package com.lnr.authentication_service.auth.infrastructure.primary;

import com.lnr.authentication_service.user.domain.user.aggrigate.User;
import com.lnr.authentication_service.user.domain.user.vo.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;
import java.util.UUID;

    public class UserMapper {

        public static User toDomain(RestRegister dto, PasswordEncoder encoder) {
            String hashed = encoder.encode(dto.password()); // infrastructure hashes
            UserPassword passwordVO = new UserPassword(hashed);

            UserImage userImage = null;
            if (dto.imageBase64() != null && dto.imageType() != null) {
                byte[] decoded = Base64.getDecoder().decode(dto.imageBase64());
                int[] intArray = new int[decoded.length];
                for (int i = 0; i < decoded.length; i++) {
                    intArray[i] = decoded[i];
                }
                userImage = new UserImage(intArray, dto.imageType());
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
                    userAddress,
                    userPhoneNumber,
                    null, // dbId handled by persistence
                     userImage
            );
        }
    }


