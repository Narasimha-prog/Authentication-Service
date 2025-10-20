package com.lnr.authentication_service.user.domain.user.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;

import java.time.Instant;

public record UserLastSeen(Instant value) {

    public UserLastSeen {
        Assert.notNull("LastSeen", value);
    }
}