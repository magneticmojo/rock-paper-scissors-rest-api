package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions;

/**
 * Exception thrown when a game is missing player two.
 */
public class MissingPlayerTwoException extends RuntimeException {
    public MissingPlayerTwoException(String errorMessage) {
        super(errorMessage);
    }
}
