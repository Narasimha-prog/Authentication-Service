package com.lnr.authentication_service.auth.infrastructure.seconadary.services;

import com.lnr.authentication_service.auth.application.AccountApplicationService;
import com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.domain.account.repository.IRefreshTokenRepository;
import com.lnr.authentication_service.auth.domain.account.services.IAuthService;
import com.lnr.authentication_service.auth.domain.account.vo.AuthTokens;
import com.lnr.authentication_service.auth.infrastructure.primary.dto.RestRegister;
import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.RefreshTokenEntity;
import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.UserAccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final JwtService jwtService;

    private final IRefreshTokenRepository refreshTokenRepository;



    @Override
    public AuthTokens generateTokensFor(UserAccount user) {

        String accessToken = jwtService.generateAccessToken(user);
        String refreshTokenStr = jwtService.generateRefreshToken(user.getPublicId().value());

        // save refresh token

           com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken token =  new RefreshToken(refreshTokenStr, user.getPublicId(),
                        Instant.now().plusMillis(jwtService.getRefreshTokenExpiration()));


              refreshTokenRepository.save(token,user);

        return new AuthTokens(accessToken, refreshTokenStr);
    }
}