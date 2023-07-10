package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game;

import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.PlayerNullException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.RockPaperScissorsGameState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.PlayerOneJoinedState;

import java.util.UUID;

/**
 * The RockPaperScissorsGame class encapsulates the state and operations of a Rock-Paper-Scissors game.
 * Each instance of this class represents a unique game, identified by a random UUID.
 * It handles player joining and move making, delegating the actual behavior to its current RockPaperScissorsGameState.
 */
public class RockPaperScissorsGame {

    private RockPaperScissorsGameState gameState;
    private final String gameId;

    public RockPaperScissorsGame(Player playerOne) {
        if (playerOne == null) {
            throw new PlayerNullException("Player cannot be null");
        }
        this.gameState = new PlayerOneJoinedState(playerOne);
        this.gameId = UUID.randomUUID().toString();
    }

    public String getId() {
        return gameId;
    }

    public RockPaperScissorsGameState getState() {
        return gameState;
    }

    public RockPaperScissorsGameState joinGame(Player playerTwo) {
        gameState = gameState.joinGame(playerTwo);
        return gameState;
    }

    public RockPaperScissorsGameState makeMove(PlayerMove playerMove) {
        gameState = gameState.makeMove(playerMove);
        return gameState;
    }
}
