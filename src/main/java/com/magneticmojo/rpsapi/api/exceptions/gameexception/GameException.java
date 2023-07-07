package com.magneticmojo.rpsapi.api.exceptions.gameexception;

/**
 * GameException is a custom runtime exception for the Rock-Paper-Scissors game application.
 * It is used to indicate that an error has occurred related to a game operation.
 * This class serves as a base exception from which other more specific game-related exceptions are derived.
 */
public class GameException extends RuntimeException {
    public GameException(String errorMessage) {
        super(errorMessage);
    }
}
