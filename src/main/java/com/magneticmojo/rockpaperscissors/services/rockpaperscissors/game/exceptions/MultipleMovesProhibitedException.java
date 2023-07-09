package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions;

/**
 * Exception thrown when a player tries to do a second move.
 * Inherits from the PlayerException class.
 */
public class MultipleMovesProhibitedException extends RockPaperScissorsGameException {

    public MultipleMovesProhibitedException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getErrorCode() {
        return "MULTIPLE_MOVES_PROHIBITED";
    }
}
