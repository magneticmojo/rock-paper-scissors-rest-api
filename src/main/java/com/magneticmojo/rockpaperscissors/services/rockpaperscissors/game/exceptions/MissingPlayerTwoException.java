package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions;

/**
 * Exception thrown when a game is missing player two. Inherits from the RockPaperScissorsGameException class.
 */
public class MissingPlayerTwoException extends RockPaperScissorsGameException {

    public MissingPlayerTwoException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getErrorCode() {
        return "MISSING_PLAYER_TWO";
    }
}
