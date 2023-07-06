package com.magneticmojo.rpsapi.api.exceptions.gameexception;

public class GameNotFoundException extends GameException {
    public GameNotFoundException(String errorMessage, String id) {
        super(errorMessage + id);
    }
}