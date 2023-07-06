package com.magneticmojo.rpsapi.api.exceptions.gameexception;

public class GameException extends RuntimeException {
    public GameException(String errorMessage) {
        super(errorMessage);
    }
}
