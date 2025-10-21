package com.lnr.authentication_service.auth.infrastructure.primary.dto;

import com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;

import java.time.Instant;
import java.util.UUID;

public record RefreshTokenRestDto(String token, String userId, String expiryDate) {

    public static RefreshTokenRestDto fromDomain(RefreshToken domain) {
        return new RefreshTokenRestDto(
                domain.getToken(),
                domain.getUserId().value().toString(),
                domain.getExpiryDate().toString()
        );
    }

    public RefreshToken toDomain() {
        return new RefreshToken(
                token,
                new UserPublicId(UUID.fromString(userId)),
                Instant.parse(expiryDate)
        );
    }
}
