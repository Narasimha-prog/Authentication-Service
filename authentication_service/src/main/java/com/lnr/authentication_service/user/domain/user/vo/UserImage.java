package com.lnr.authentication_service.user.domain.user.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;

public record UserImage(int[] value, String imagetype) {
    public UserImage {
        Assert.notNull("ImageValue",value);
        Assert.field("ImageType",imagetype).notBlank();
    }
}
