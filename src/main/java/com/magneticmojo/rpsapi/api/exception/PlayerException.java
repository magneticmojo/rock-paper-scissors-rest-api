package com.magneticmojo.rpsapi.api.exception;

public class PlayerException extends RuntimeException {
        public PlayerException(String errorMessage) {
            super(errorMessage);
        }
}
