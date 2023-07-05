package com.example.rpsapi.api.model.entities;

import com.example.rpsapi.api.state.GameCreatedState;
import com.example.rpsapi.api.state.GameState;

import java.util.UUID;

public class RockPaperScissorsGame {

    private GameState gameState;
    private final String gameId;

    public RockPaperScissorsGame(Player playerOne) {
        this.gameState = new GameCreatedState(playerOne);
        this.gameId = UUID.randomUUID().toString(); // todo -> ToString || ToJson || UUID
    }

    public String getGameID() {
        return gameId;
    }

    public GameState getState() {
        return gameState;
    }

    // todo -> Testa med alla states -> GameCreatedState, GameReadyState, GameActiveState, GameEndedState
    public GameState joinGame(Player playerTwo) {
        gameState = gameState.joinGame(playerTwo);
        return gameState;
    }

    // todo -> Testa med alla states -> GameCreatedState, GameReadyState, GameActiveState, GameEndedState
    public GameState makeMove(String playerName, Move move) {
        gameState = gameState.makeMove(playerName, move);
        return gameState;
    }
}
