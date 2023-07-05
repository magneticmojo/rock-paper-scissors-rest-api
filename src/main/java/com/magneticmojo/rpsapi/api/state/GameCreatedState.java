package com.magneticmojo.rpsapi.api.state;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rpsapi.api.exception.MoveProhibitedMissingPlayerException;
import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import com.magneticmojo.rpsapi.api.serialization.GameCreatedStateSerializer;

@JsonSerialize(using = GameCreatedStateSerializer.class)
public record GameCreatedState(Player playerOne) implements GameState {

    private static final String DUPLICATE_NAME_SUFFIX = "2";

    @Override
    public GameState joinGame(Player playerTwo) {

        if (isDuplicateName(playerTwo)) {
            playerTwo = createPlayerWithNameSuffix(playerTwo);
        }

        return new GameReadyState(playerOne, playerTwo);
    }

    private boolean isDuplicateName(Player playerTwo) {
        return playerOne.name().equals(playerTwo.name());
    }

    private Player createPlayerWithNameSuffix(Player playerTwo) {
        return new Player(playerTwo.name() + DUPLICATE_NAME_SUFFIX);
    }

    @Override
    public GameState makeMove(PlayerMove playerMove) {
        throw new MoveProhibitedMissingPlayerException("MISSING PLAYER. MOVE PROHIBITED.");
    }
}