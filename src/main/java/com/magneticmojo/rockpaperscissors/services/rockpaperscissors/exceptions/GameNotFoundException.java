package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.exceptions;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String errorMessage, String id) {
        super(errorMessage + id);
    }
}