package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions;

/**
 * Exception thrown when a player is not in the game.
 * Inherits from the PlayerException class.
 */
public class PlayerNotInGameException extends RockPaperScissorsGameException {

    public PlayerNotInGameException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getErrorCode() {
        return "PLAYER_NOT_IN_GAME";
    }
}
