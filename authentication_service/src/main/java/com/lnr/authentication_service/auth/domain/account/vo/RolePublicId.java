package com.lnr.authentication_service.auth.domain.account.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;

import java.util.UUID;

public record RolePublicId(UUID value) {
    public RolePublicId {
        Assert.notNull("RolePublicId",value);
    }
}
