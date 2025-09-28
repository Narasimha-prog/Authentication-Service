package com.lnr.authentication_service.shared.error.domain;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String email) {
        super("Invalid email: " + email);
    }

    public InvalidEmailException(String email, String message) {
        super("Invalid email '" + email + "': " + message);
    }
}
