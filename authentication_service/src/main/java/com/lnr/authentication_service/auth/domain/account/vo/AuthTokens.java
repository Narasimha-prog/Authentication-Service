package com.lnr.authentication_service.auth.domain.account.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;

import java.util.UUID;

public record AuthTokens(UUID userPublicId, String accessToken, String refreshToken) {
    public AuthTokens {
        Assert.notNull("UserPublicId",userPublicId);
        Assert.field("accesstoken",accessToken).minLength(5);
        Assert.field("refreshtoken",accessToken).minLength(5);
    }
}
