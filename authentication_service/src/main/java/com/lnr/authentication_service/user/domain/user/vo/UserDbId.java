package com.lnr.authentication_service.user.domain.user.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;
import org.jilt.Builder;

@Builder
public record UserDbId(long value ) {
    public UserDbId {
        Assert.field("UserDatabaseId",value).positive();
    }
}
