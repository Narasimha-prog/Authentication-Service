package com.lnr.authentication_service.auth.application;

import com.lnr.authentication_service.auth.infrastructure.primary.JwtService;
import com.lnr.authentication_service.user.application.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthApplicationService {

    private final UserApplicationService userService;
    private final JwtService jwtService;
}
