package com.lnr.authentication_service.auth.infrastructure.primary.dto;

import com.lnr.authentication_service.auth.domain.account.vo.AuthTokens;
import lombok.Builder;

@Builder
public record RestAuthTokens(String accessToken, String refreshToken) {

    public static RestAuthTokens fromDomain(AuthTokens authTokens){

       return  new RestAuthTokens(authTokens.accessToken(), authTokens.refreshToken());
    }
}
