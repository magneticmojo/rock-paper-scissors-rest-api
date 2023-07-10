package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.exceptions;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public String getErrorCode() {
        return "GAME_NOT_FOUND";
    }
}