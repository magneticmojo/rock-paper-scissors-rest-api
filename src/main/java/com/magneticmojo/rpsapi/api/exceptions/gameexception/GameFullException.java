package com.magneticmojo.rpsapi.api.exceptions.gameexception;

/**
 * Exception thrown when a game is already full.
 * Inherits from the GameException class.
 */
public class GameFullException extends GameException {
    public GameFullException(String errorMessage) {
        super(errorMessage);
    }
}
