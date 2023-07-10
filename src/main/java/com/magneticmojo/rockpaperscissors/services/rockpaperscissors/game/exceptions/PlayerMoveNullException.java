package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions;

/**
 * Exception thrown when a player move is null.
 * Inherits from the RockPaperScissorsGameException class.
 */
public class PlayerMoveNullException extends RockPaperScissorsGameException {

    public PlayerMoveNullException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getErrorCode() {
        return "PLAYER_MOVE_NULL";
    }
}
