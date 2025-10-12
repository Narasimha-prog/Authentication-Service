package com.lnr.authentication_service.user.domain.user.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;

public record UserPhoneNumber(String countryCode, long number) {
    public UserPhoneNumber {
        Assert.field("Country",countryCode)
                .minLength(FieldLength.COUNTRY_CODE.getMin())
                .maxLength(FieldLength.COUNTRY_CODE.getMax());
        Assert.field("Number",number)
                .positive()
                .min(FieldLength.PHONE_NUMBER.getMin())
                .max(FieldLength.PHONE_NUMBER.getMax());
    }
}
