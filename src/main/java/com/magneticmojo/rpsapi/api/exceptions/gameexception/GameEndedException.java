package com.magneticmojo.rpsapi.api.exceptions.gameexception;

public class GameEndedException extends GameException {
    public GameEndedException(String errorMessage) {
        super(errorMessage);
    }
}
