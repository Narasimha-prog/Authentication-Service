package com.lnr.authentication_service.auth.infrastructure.seconadary.repository;

import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IJpaAccountRepository extends JpaRepository<UserAccountEntity,Long> {

    Optional<UserAccountEntity> findByEmail(String email);

    Optional<UserAccountEntity> findByPublicId(UUID publicId);

}
