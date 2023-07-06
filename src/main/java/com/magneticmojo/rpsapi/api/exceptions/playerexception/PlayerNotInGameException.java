package com.magneticmojo.rpsapi.api.exceptions.playerexception;

public class PlayerNotInGameException extends PlayerException {
    public PlayerNotInGameException(String errorMessage) {
        super(errorMessage);
    }
}
