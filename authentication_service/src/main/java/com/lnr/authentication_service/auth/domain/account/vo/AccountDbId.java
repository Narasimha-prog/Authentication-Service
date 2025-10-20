package com.lnr.authentication_service.auth.domain.account.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;

public record AccountDbId(long value) {
    public AccountDbId {
        Assert.field("AccountDatabaseId",value).positive();
    }
}
