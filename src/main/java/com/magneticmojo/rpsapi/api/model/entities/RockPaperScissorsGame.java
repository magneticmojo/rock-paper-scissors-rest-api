package com.magneticmojo.rpsapi.api.model.entities;

import com.magneticmojo.rpsapi.api.state.PlayerOneJoinedState;
import com.magneticmojo.rpsapi.api.state.GameState;

import java.util.UUID;

/**
 * The RockPaperScissorsGame class encapsulates the state and operations of a Rock-Paper-Scissors game.
 * Each instance of this class represents a unique game, identified by a random UUID.
 * It handles player joining and move making, delegating the actual behavior to its current GameState.
 */
public class RockPaperScissorsGame {

    private GameState gameState;
    private final String gameId;

    public RockPaperScissorsGame(Player playerOne) {
        this.gameState = new PlayerOneJoinedState(playerOne);
        this.gameId = UUID.randomUUID().toString();
    }

    public String getId() {
        return gameId;
    }

    public GameState getState() {
        return gameState;
    }

    public GameState joinGame(Player playerTwo) {
        gameState = gameState.joinGame(playerTwo);
        return gameState;
    }

    public GameState makeMove(PlayerMove playerMove) {
        gameState = gameState.makeMove(playerMove);
        return gameState;
    }
}
