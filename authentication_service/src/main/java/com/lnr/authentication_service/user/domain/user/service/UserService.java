package com.lnr.authentication_service.user.domain.user.service;


import com.lnr.authentication_service.user.domain.user.aggrigate.User;
import com.lnr.authentication_service.user.domain.user.repository.IUserRepository;
import com.lnr.authentication_service.user.domain.user.vo.UserEmail;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final IUserRepository iUserRepository;


     public Optional<User> findByEmail(UserEmail userEmail){
        return iUserRepository.findByEmail(userEmail);
    }




}
