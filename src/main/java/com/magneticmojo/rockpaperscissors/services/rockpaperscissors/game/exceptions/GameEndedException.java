package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions;

/**
 * Exception thrown when a game has already ended.
 * Inherits from the RockPaperScissorsGameException class.
 */
public class GameEndedException extends RockPaperScissorsGameException {

    public GameEndedException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getErrorCode() {
        return "GAME_ENDED";
    }
}
