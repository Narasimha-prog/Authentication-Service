package com.lnr.authentication_service.user.domain.user.service;


import com.lnr.authentication_service.user.domain.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

    private final IUserRepository iUserRepository;



}
