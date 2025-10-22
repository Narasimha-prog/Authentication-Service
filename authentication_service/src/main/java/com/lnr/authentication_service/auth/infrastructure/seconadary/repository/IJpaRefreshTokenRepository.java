package com.lnr.authentication_service.auth.infrastructure.seconadary.repository;

import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJpaRefreshTokenRepository extends JpaRepository<RefreshTokenEntity,Long> {
}
