package com.lnr.authentication_service.user.infrastrature.seconadary.repository;

import com.lnr.authentication_service.user.infrastrature.seconadary.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByEmail(String entity);

    Optional<UserEntity> findByPublicId(UUID publicId);
}
