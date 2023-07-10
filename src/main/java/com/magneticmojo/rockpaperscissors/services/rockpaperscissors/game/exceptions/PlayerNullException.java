package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions;

public class PlayerNullException extends RockPaperScissorsGameException {

    public PlayerNullException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getErrorCode() {
        return "PLAYER_NULL";
    }
}
