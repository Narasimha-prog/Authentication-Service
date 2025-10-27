package com.lnr.authentication_service.auth.domain.account.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;
import org.jilt.Builder;

import java.util.UUID;

@Builder
public record RefreshPublicId(UUID value) {
    public RefreshPublicId {
        Assert.notNull("RefreshPublicId",value);
    }
}
