package com.magneticmojo.rpsapi.api.exception;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(String gameNotFound) {
        super(gameNotFound);
    }
}
