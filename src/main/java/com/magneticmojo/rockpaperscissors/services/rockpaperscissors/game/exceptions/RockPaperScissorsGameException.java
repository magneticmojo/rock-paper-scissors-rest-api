package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions;

public abstract class RockPaperScissorsGameException extends RuntimeException {

    public abstract String getErrorCode();

    public RockPaperScissorsGameException(String errorMessage) {
        super(errorMessage);
    }
}
