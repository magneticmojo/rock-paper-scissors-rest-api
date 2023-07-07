package com.magneticmojo.rpsapi.api.exceptions.gameexception;

/**
 * Exception thrown when a game has already ended.
 * Inherits from the GameException class.
 */
public class GameEndedException extends GameException {
    public GameEndedException(String errorMessage) {
        super(errorMessage);
    }
}
