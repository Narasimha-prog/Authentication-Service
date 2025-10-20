package com.lnr.authentication_service.auth.domain.account.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AuthorityName {
    USER_READ,
    USER_WRITE,
    ORDER_CREATE,
    ORDER_READ,
    UNKNOWN;

    private static final String PREFIX = "AUTH_";
    private static final Map<String, AuthorityName> AUTHORITIES = buildAuthorities();

    private static Map<String, AuthorityName> buildAuthorities() {
        return Stream.of(values())
                .collect(Collectors.toUnmodifiableMap(AuthorityName::key, Function.identity()));
    }

    public String key() {
        return PREFIX + name();
    }

    public static AuthorityName from(String authority) {
        Assert.notBlank("authority", authority);
        return AUTHORITIES.getOrDefault(authority, UNKNOWN);
    }
}
