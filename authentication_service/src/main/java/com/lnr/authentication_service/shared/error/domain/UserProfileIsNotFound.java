package com.lnr.authentication_service.shared.error.domain;

public class UserProfileIsNotFound extends RuntimeException {
    public UserProfileIsNotFound(String message) {
        super(message);
    }
}
