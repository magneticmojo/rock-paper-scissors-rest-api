package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.exceptions;
/**
 * This class represents an exception that is thrown when a game is not found in the repository.
 * */
public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public String getErrorCode() {
        return "GAME_NOT_FOUND";
    }
}