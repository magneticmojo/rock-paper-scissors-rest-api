package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions;

/**
 * Exception thrown when a player is null.
 * Inherits from the RockPaperScissorsGameException class.
 */
public class PlayerNullException extends RockPaperScissorsGameException {

    public PlayerNullException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getErrorCode() {
        return "PLAYER_NULL";
    }
}
