package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions;

/**
 * Exception thrown when a game is already full.
 * Inherits from the GameException class.
 */
public class GameFullException extends RuntimeException {
    public GameFullException(String errorMessage) {
        super(errorMessage);
    }
}
