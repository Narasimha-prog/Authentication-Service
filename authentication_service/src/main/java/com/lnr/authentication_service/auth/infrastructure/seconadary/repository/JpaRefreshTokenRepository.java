package com.lnr.authentication_service.auth.infrastructure.seconadary.repository;

import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaRefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByPublicId(UUID publicId);

    void deleteByPublicId(UUID publicId);

    boolean existsByPublicId(UUID publicId);

    Optional<RefreshTokenEntity> findByToken(String token); // optional if needed
}