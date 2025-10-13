package com.lnr.authentication_service.user.infrastrature.seconadary;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity,Long> {
}
