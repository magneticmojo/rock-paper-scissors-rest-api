package com.magneticmojo.rpsapi.api.exceptions.playerexception;

/**
 * Exception thrown when a player tries to do a second move.
 * Inherits from the PlayerException class.
 */
public class MultipleMovesProhibitedException extends PlayerException {
    public MultipleMovesProhibitedException(String errorMessage) {
        super(errorMessage);
    }
}
