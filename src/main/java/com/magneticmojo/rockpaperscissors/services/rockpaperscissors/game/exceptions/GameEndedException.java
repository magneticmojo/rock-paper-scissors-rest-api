package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions;

/**
 * Exception thrown when a game has already ended.
 * Inherits from the GameException class.
 */
public class GameEndedException extends RuntimeException {
    public GameEndedException(String errorMessage) {
        super(errorMessage);
    }
}
