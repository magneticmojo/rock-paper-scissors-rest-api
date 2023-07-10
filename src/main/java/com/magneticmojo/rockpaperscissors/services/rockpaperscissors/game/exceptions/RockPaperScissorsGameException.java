package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions;

/**
 * This is an abstract class representing the generic exceptions related to the Rock-Paper-Scissors game.
 * All game-specific exceptions extend this class, providing a custom error code through the getErrorCode() method.
 */
public abstract class RockPaperScissorsGameException extends RuntimeException {

    public abstract String getErrorCode();

    public RockPaperScissorsGameException(String errorMessage) {
        super(errorMessage);
    }
}
