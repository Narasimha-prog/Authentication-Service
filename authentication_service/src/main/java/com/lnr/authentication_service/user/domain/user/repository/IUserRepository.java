package com.lnr.authentication_service.user.domain.user.repository;

import com.lnr.authentication_service.user.domain.user.aggrigate.User;
import com.lnr.authentication_service.user.domain.user.vo.UserEmail;
import com.lnr.authentication_service.user.domain.user.vo.UserPublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    User save(User user);// store user in DB
    Optional<User> get(UserPublicId publicId);
    Optional<User>findByEmail(UserEmail email);// fetch user by email for login
    Page<User> findAll(Pageable pageable);
}
