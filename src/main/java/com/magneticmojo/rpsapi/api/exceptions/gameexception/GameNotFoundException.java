package com.magneticmojo.rpsapi.api.exceptions.gameexception;

/**
 * Exception thrown when a game is not found.
 * Inherits from the GameException class.
 */
public class GameNotFoundException extends GameException {
    public GameNotFoundException(String errorMessage, String id) {
        super(errorMessage + id);
    }
}