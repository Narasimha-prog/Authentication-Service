package com.lnr.authentication_service.auth.domain.account.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;

public record RefreshDbId(long value) {
    public RefreshDbId {
        Assert.field("RefreshDatabaseId",value).positive();
    }
}
