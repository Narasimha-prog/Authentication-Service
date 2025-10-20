package com.lnr.authentication_service.user.infrastrature.seconadary.repository;

import com.lnr.authentication_service.user.infrastrature.seconadary.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserProfileEntity,Long> {

    Optional<UserProfileEntity> findByEmail(String entity);

    Optional<UserProfileEntity> findByPublicId(UUID publicId);



}
