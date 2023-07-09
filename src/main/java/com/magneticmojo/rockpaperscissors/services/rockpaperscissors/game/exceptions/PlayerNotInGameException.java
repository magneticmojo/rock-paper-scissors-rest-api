package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions;

/**
 * Exception thrown when a player is not in the game.
 * Inherits from the PlayerException class.
 */
public class PlayerNotInGameException extends RuntimeException {
    public PlayerNotInGameException(String errorMessage) {
        super(errorMessage);
    }
}
