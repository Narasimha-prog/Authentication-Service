package com.lnr.authentication_service.user.domain.profile.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;
import org.jilt.Builder;

import java.util.UUID;

@Builder
public record UserPublicId(UUID value) {

    public UserPublicId {
        Assert.notNull("UserPublicId",value);
    }
}
