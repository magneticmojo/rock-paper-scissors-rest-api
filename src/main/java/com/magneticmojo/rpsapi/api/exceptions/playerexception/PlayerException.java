package com.magneticmojo.rpsapi.api.exceptions.playerexception;

public class PlayerException extends RuntimeException {
        public PlayerException(String errorMessage) {
            super(errorMessage);
        }
}
