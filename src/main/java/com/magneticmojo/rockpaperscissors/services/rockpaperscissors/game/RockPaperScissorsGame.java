package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game;

import com.magneticmojo.rockpaperscissors.api.model.responses.GameStateResponse;
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

    private RockPaperScissorsGameState rockPaperScissorsGameState;
    private final String gameId;

    public RockPaperScissorsGame(Player playerOne) {
        this.rockPaperScissorsGameState = new PlayerOneJoinedState(playerOne);
        this.gameId = UUID.randomUUID().toString();
    }

    public String getId() {
        return gameId;
    }

    public RockPaperScissorsGameState getState() {
        return rockPaperScissorsGameState;
    } // TODO Change to something making it clear the state is allowed to be publicly exposed

    public GameStateResponse joinGame(Player playerTwo) {
        rockPaperScissorsGameState = rockPaperScissorsGameState.joinGame(playerTwo);
        // Output the game state to the console
        // Game id + player
        return null; // Return GameStateResponse + Serializera det till JSON
    }

    public GameStateResponse makeMove(PlayerMove playerMove) {

        rockPaperScissorsGameState = rockPaperScissorsGameState.makeMove(playerMove);
        return null; // Return GameStateResponse + Serializera det till JSON
    }
}
