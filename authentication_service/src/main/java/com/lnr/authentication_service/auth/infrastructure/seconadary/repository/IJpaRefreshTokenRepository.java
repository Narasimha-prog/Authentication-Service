package com.lnr.authentication_service.auth.infrastructure.seconadary.repository;

import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.RefreshTokenEntity;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IJpaRefreshTokenRepository extends JpaRepository<RefreshTokenEntity,Long> {
    @Query("SELECT r FROM RefreshTokenEntity r WHERE r.user.publicId = :userPublicId ORDER BY r.expiryDate DESC")
    List<RefreshTokenEntity> findAllByUserPublicId(UUID userPublicId);

    Optional<RefreshTokenEntity> findByPublicId(UUID publicId);
    void deleteByPublicId(UUID publicId);
    Optional<RefreshTokenEntity> findByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshTokenEntity r WHERE r.user.publicId = :userPublicId")
    void deleteAllByUserPublicId(UUID userPublicId);
}
