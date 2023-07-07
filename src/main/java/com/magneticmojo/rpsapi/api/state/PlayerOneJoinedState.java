package com.magneticmojo.rpsapi.api.state;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rpsapi.api.exceptions.gameexception.GameNotFullException;
import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import com.magneticmojo.rpsapi.api.serialization.PlayerOneJoinedStateSerializer;

/**
 * The PlayerOneJoinedState class represents the game state when only the first player has joined a game of Rock-Paper-Scissors.
 * It allows the second player to join the game, but prohibits moves until the game is fully populated.
 * Duplicate player names are managed by appending a suffix to the second player's name.
 * The class implements the GameState interface, thus representing a concrete state of the State Pattern.
 */
@JsonSerialize(using = PlayerOneJoinedStateSerializer.class)
public record PlayerOneJoinedState(Player playerOne) implements GameState {

    @Override
    public GameState joinGame(Player playerTwo) {

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
    public GameState makeMove(PlayerMove playerMove) {
        throw new GameNotFullException("Move prohibited. Player two not joined");
    }
}