package com.lnr.authentication_service.auth.domain.user.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;
import org.jilt.Builder;

import static com.lnr.authentication_service.auth.domain.user.vo.FieldLength.FIRST_NAME;

@Builder
public record UserFirstName(String value) {
    public UserFirstName {

        Assert.field("UserFirstName",value)
                .notNull().notBlank()
                .minLength(FIRST_NAME.getMin())
                .maxLength(FIRST_NAME.getMax());
    }
}
