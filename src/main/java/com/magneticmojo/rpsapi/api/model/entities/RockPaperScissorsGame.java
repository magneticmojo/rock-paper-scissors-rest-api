package com.magneticmojo.rpsapi.api.model.entities;

import com.magneticmojo.rpsapi.api.state.GameCreatedState;
import com.magneticmojo.rpsapi.api.state.GameState;

import java.util.UUID;

public class RockPaperScissorsGame { // TODO @TEST

    private GameState gameState;
    private final String gameId;

    public RockPaperScissorsGame(Player playerOne) {
        this.gameState = new GameCreatedState(playerOne);
        this.gameId = UUID.randomUUID().toString(); // todo -> ToString || ToJson || UUID
    }

    public String getId() {
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
    public GameState makeMove(PlayerMove playerMove) {
        gameState = gameState.makeMove(playerMove);
        return gameState;
    }
}
