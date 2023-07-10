package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions;

public class PlayerMoveNullException extends RockPaperScissorsGameException {

    public PlayerMoveNullException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getErrorCode() {
        return null;
    }
}
