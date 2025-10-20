package com.lnr.authentication_service.auth.domain.account.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;

public record UserPassword(String value) {

    public UserPassword {
        Assert.field("UserPassword",value).minLength(3);
    }
}
