package com.lnr.authentication_service.user.domain.profile.repository;

import com.lnr.authentication_service.user.domain.user.aggrigate.User;
import com.lnr.authentication_service.auth.domain.account.vo.UserEmail;
import com.lnr.authentication_service.user.domain.profile.vo.UserPublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserRepository {
    void save(User user);// store user in DB
    Optional<User> get(UserPublicId publicId);
    Optional<User>findByEmail(UserEmail email);// fetch user by email for login
    Page<User> findAll(Pageable pageable);
}
