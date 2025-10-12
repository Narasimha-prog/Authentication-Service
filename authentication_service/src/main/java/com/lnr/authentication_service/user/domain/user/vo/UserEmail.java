package com.lnr.authentication_service.user.domain.user.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;
import org.jilt.Builder;

import static com.lnr.authentication_service.user.domain.user.vo.FieldLength.EMAIL;


@Builder
public record UserEmail(String value) {
    // Simple regex for email validation

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public UserEmail {
        Assert.field("Email", value)
                .minLength(EMAIL.getMin())
                .maxLength(EMAIL.getMax());

        if (!value.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Invalid email format: " + value);
        }
    }
}
