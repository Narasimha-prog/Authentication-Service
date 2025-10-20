package com.lnr.authentication_service.auth.domain.account.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;

public record AuthDbId(long value) {
    public AuthDbId {
        Assert.field("AuthorityDatabaseId",value).positive();
    }
}
