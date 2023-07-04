package com.example.rpsapi.api.exception;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(String game_does_not_exist) {
    }
}
