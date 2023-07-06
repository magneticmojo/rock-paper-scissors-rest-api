package com.magneticmojo.rpsapi.api.exceptions.playerexception;

public class MultipleMovesProhibitedException extends PlayerException {
    public MultipleMovesProhibitedException(String errorMessage) {
        super(errorMessage);
    }
}
