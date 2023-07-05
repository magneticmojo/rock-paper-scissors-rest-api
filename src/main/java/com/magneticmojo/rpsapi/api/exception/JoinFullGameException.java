package com.magneticmojo.rpsapi.api.exception;

public class JoinFullGameException extends RuntimeException {
    public JoinFullGameException(String errorMessage) {
        super(errorMessage);
    }
}
