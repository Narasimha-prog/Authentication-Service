package com.lnr.authentication_service.auth.domain.account.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;

public record AuthTokens(String accessToken, String refreshToken) {
    public AuthTokens {
        Assert.field("accesstoken",accessToken).minLength(5);
        Assert.field("refreshtoken",accessToken).minLength(5);
    }
}
