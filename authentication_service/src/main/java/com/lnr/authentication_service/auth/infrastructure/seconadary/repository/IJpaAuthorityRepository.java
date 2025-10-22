package com.lnr.authentication_service.auth.infrastructure.seconadary.repository;

import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJpaAuthorityRepository extends JpaRepository<AuthorityEntity,Long> {
}
