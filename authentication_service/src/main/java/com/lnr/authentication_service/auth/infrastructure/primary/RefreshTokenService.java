package com.lnr.authentication_service.auth.infrastructure.primary;


import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.RefreshTokenEntity;
import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.UserAccountEntity;
import com.lnr.authentication_service.auth.infrastructure.seconadary.repository.JpaRefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final JpaRefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public RefreshTokenService(JpaRefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    public RefreshTokenEntity createRefreshToken(UserAccountEntity user) {
        UUID userId = user.getPublicId();
        String token = jwtService.generateRefreshToken(userId);

        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .publicId(UUID.randomUUID())
                .user(user)
                .token(token)
                .expiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60)) // 7 days
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public boolean isValid(String token) {
        return refreshTokenRepository.findByToken(token)
                .filter(rt -> rt.getExpiryDate().isAfter(Instant.now()))
                .isPresent();
    }

    public void revokeToken(UUID token) {
        refreshTokenRepository.deleteByPublicId(token);
    }
}

