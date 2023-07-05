package com.magneticmojo.rpsapi.api.exception;

public class MoveProhibitedMissingPlayerException extends RuntimeException {
    public MoveProhibitedMissingPlayerException(String errorMessage) {
        super(errorMessage);
    }
}
