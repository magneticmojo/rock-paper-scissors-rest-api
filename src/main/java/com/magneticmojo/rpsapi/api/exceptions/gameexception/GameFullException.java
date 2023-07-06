package com.magneticmojo.rpsapi.api.exceptions.gameexception;

public class GameFullException extends GameException {
    public GameFullException(String errorMessage) {
        super(errorMessage);
    }
}
