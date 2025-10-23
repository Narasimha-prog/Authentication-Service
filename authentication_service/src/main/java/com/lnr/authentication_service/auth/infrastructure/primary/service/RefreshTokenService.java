package com.lnr.authentication_service.auth.infrastructure.primary.service;


import com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.domain.account.services.ITokenService;
import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.RefreshTokenEntity;
import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.UserAccountEntity;
import com.lnr.authentication_service.auth.infrastructure.seconadary.repository.IJpaRefreshTokenRepository;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService implements ITokenService {

    private final IJpaRefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public RefreshTokenService(IJpaRefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    public RefreshToken createRefreshToken(UserAccount user) {
        UUID userId = user.getPublicId().value();

        String token = jwtService.generateRefreshToken(userId);

        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .publicId(UUID.randomUUID())
                .user(UserAccountEntity.fromDomain(user))
                .token(token)
                .expiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60)) // 7 days
                .build();

        return RefreshTokenEntity.toDomain(refreshTokenRepository.save(refreshToken));
    }


    public Optional<RefreshToken> findByPublicId(UserPublicId publicId) {
        return refreshTokenRepository.findByPublicId(publicId.value()).map(RefreshTokenEntity::toDomain);
    }

//    public void revokeToken(UUID token) {
//        refreshTokenRepository.deleteByPublicId(token);
//    }
}

