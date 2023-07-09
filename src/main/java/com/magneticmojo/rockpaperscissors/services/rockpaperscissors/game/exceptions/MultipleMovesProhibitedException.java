package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions;

/**
 * Exception thrown when a player tries to do a second move.
 * Inherits from the PlayerException class.
 */
public class MultipleMovesProhibitedException extends RuntimeException {
    public MultipleMovesProhibitedException(String errorMessage) {
        super(errorMessage);
    }
}
