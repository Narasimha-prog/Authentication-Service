package com.lnr.authentication_service.shared.error.domain;

public class UserAccountNotFound extends RuntimeException {
    public UserAccountNotFound(String message) {
        super(message);
    }
}
