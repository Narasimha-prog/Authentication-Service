package com.lnr.authentication_service.auth.domain.user.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;
import org.jilt.Builder;

@Builder
public record UserLastName(String value) {
    public UserLastName {
        Assert.field("UserLastName",value)
                .notNull()
                .notBlank()
                .minLength(FieldLength.LAST_NAME.getMin())
                .maxLength(FieldLength.LAST_NAME.getMax());
    }
}
