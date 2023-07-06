package com.magneticmojo.rpsapi.api.exceptions.gameexception;

public class GameNotFullException extends GameException {
    public GameNotFullException(String errorMessage) {
        super(errorMessage);
    }
}
