package com.magneticmojo.rpsapi.api.exception;

public class MaxPlayerLimitReachedException extends RuntimeException {
    public MaxPlayerLimitReachedException(String errorMessage) {
        super(errorMessage);
    }
}
