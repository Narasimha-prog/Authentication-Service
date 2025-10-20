package com.lnr.authentication_service.user.domain.profile.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;
import org.jilt.Builder;

@Builder
public record UserLastName(String value) {
    public UserLastName {
        Assert.field("UserLastName",value)
                .minLength(FieldLength.LAST_NAME.getMin())
                .maxLength(FieldLength.LAST_NAME.getMax());
    }
}
