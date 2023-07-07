package com.magneticmojo.rpsapi.api.exceptions.playerexception;

/**
 * PlayerException is a custom runtime exception for the Rock-Paper-Scissors game application.
 * It is used to signify an error condition related to a player's actions or status within a game.
 * This class serves as a base exception from which other more specific player-related exceptions are derived.
 */
public class PlayerException extends RuntimeException {
    public PlayerException(String errorMessage) {
        super(errorMessage);
    }
}
