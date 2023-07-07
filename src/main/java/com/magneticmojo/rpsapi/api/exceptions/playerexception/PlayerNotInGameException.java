package com.magneticmojo.rpsapi.api.exceptions.playerexception;

/**
 * Exception thrown when a player is not in the game.
 * Inherits from the PlayerException class.
 */
public class PlayerNotInGameException extends PlayerException {
    public PlayerNotInGameException(String errorMessage) {
        super(errorMessage);
    }
}
