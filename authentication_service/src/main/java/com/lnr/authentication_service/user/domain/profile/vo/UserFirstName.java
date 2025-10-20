package com.lnr.authentication_service.user.domain.profile.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;
import org.jilt.Builder;

import static com.lnr.authentication_service.user.domain.profile.vo.FieldLength.FIRST_NAME;

@Builder
public record UserFirstName(String value) {
    public UserFirstName {

        Assert.field("UserFirstName",value)
                .minLength(FIRST_NAME.getMin())
                .maxLength(FIRST_NAME.getMax());
    }
}
