package com.example.rpsapi.api.model.entities;

import com.example.rpsapi.api.state.GameCreatedState;
import com.example.rpsapi.api.state.GameState;

import java.util.UUID;

public class RPSGame {

    private GameState gameState;
    private final String gameId;

    public RPSGame(Player playerOne) {
        this.gameState = new GameCreatedState(playerOne);
        this.gameId = UUID.randomUUID().toString();
    }

    public String getGameID() {
        return gameId;
    }

    public GameState getState() {
        return gameState;
    }

    public GameState joinGame(Player playerTwo) {
        gameState = gameState.joinGame(playerTwo);
        return gameState;
    }

    public GameState makeMove(String playerName, Move move) {
        gameState = gameState.makeMove(playerName, move);
        return gameState;
    }
}
