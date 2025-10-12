package com.lnr.authentication_service.user.domain.user.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;
import org.jilt.Builder;

import static com.lnr.authentication_service.user.domain.user.vo.FieldLength.ADDRESS;

@Builder
public record UserAddress(String street,String city,String zipCode,String country) {
    public UserAddress {
        Assert.field("Street",street)
                .notNull()
                .notBlank()
                .minLength(ADDRESS.getMin())
                .maxLength(ADDRESS.getMax());
        Assert.field("City",city)
                .notNull()
                .notBlank()
                .minLength(ADDRESS.getMin())
                .maxLength(ADDRESS.getMax());
        Assert.field("ZipCode",zipCode)
                .notNull()
                .notBlank()
                .minLength(ADDRESS.getMin())
                .maxLength(ADDRESS.getMax());
        Assert.field("Country",country)
                .notNull()
                .notBlank()
                .minLength(ADDRESS.getMin())
                .maxLength(ADDRESS.getMax());

    }
}
