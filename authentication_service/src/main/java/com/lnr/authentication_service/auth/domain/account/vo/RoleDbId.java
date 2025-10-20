package com.lnr.authentication_service.auth.domain.account.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;

public record RoleDbId(long value) {
    public RoleDbId {
        Assert.field("RoleDatabaseId",value).positive();
    }
}
