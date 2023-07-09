package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.MissingPlayerTwoException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;
import com.magneticmojo.rockpaperscissors.api.serialization.gamestates.PlayerOneJoinedStateSerializer;

/**
 * The PlayerOneJoinedState class represents the game state when only the first player has joined a game of Rock-Paper-Scissors.
 * It allows the second player to join the game, but prohibits moves until the game is fully populated.
 * Duplicate player names are managed by appending a suffix to the second player's name.
 * The class implements the RockPaperScissorsGameState interface, thus representing a concrete state of the State Pattern.
 */
@JsonSerialize(using = PlayerOneJoinedStateSerializer.class)
public class PlayerOneJoinedState implements RockPaperScissorsGameState {

    private final Player playerOne;

    public PlayerOneJoinedState(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    @Override
    public RockPaperScissorsGameState joinGame(Player playerTwo) {

        if (isDuplicateName(playerTwo)) {
            playerTwo = createPlayerWithNameSuffix(playerTwo);
        }

        return new PlayerTwoJoinedState(playerOne, playerTwo);
    }

    private boolean isDuplicateName(Player playerTwo) {
        return playerOne.name().equals(playerTwo.name());
    }

    private Player createPlayerWithNameSuffix(Player playerTwo) {
        return new Player(playerTwo.name() + getDuplicateNameSuffix());
    }

    private String getDuplicateNameSuffix() {
        return "2";
    }

    @Override
    public RockPaperScissorsGameState makeMove(PlayerMove playerMove) {
        throw new MissingPlayerTwoException("Move prohibited. Player two not joined");
    }
}