package com.magneticmojo.rpsapi.api.exceptions.gameexception;

/**
 * Exception thrown when a game is not full.
 * Inherits from the GameException class.
 */
public class GameNotFullException extends GameException {
    public GameNotFullException(String errorMessage) {
        super(errorMessage);
    }
}
