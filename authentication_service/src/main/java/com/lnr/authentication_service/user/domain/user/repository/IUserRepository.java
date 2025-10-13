package com.lnr.authentication_service.user.domain.user.repository;

import com.lnr.authentication_service.user.domain.user.aggrigate.User;

public interface IUserRepository {
    User save(User user);  // store user in DB
    User findByEmail(String email); // fetch user by email for login
}
