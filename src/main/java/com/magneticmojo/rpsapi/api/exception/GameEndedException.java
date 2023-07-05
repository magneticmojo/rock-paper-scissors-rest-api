package com.magneticmojo.rpsapi.api.exception;

public class GameEndedException extends RuntimeException {
    public GameEndedException(String errorMessage) {
        super(errorMessage);
    }
}
