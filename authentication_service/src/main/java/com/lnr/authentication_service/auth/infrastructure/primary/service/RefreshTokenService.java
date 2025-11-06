package com.lnr.authentication_service.auth.infrastructure.primary.service;


import com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.domain.account.services.ITokenService;
import com.lnr.authentication_service.auth.domain.account.vo.RefreshPublicId;
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


    public RefreshTokenService(IJpaRefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;

    }


    @Override
    public void revokeTokensByUser(UserPublicId userPublicId) {
         refreshTokenRepository.deleteAllByUserPublicId(userPublicId.value());
    }

    @Override
    public void revokeToken(RefreshPublicId tokenPublicId) {
      refreshTokenRepository.deleteByPublicId(tokenPublicId.value());
    }

    @Override
    public void saveTokens(UserAccount account) {
        
    }

    @Override
    public Optional<RefreshToken> findByPublicId(RefreshPublicId publicId) {
        return refreshTokenRepository.findByPublicId(publicId.value()).map(RefreshTokenEntity::toDomain);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
       return refreshTokenRepository.findByToken(token).map(RefreshTokenEntity::toDomain);
    }


    public void saveSpringRefreshToken(UserAccount user, String tokenValue, Instant expiryDate) {
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .publicId(UUID.randomUUID())
                .user(UserAccountEntity.fromDomain(user))
                .token(tokenValue)
                .expiryDate(expiryDate)
                .build();

        refreshTokenRepository.save(refreshToken);
    }


    public Optional<RefreshToken> findByPublicId(UserPublicId publicId) {
        return refreshTokenRepository.findByPublicId(publicId.value()).map(RefreshTokenEntity::toDomain);
    }


}

