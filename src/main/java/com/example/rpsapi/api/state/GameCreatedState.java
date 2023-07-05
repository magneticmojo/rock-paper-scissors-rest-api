package com.example.rpsapi.api.state;

import com.example.rpsapi.api.model.entities.Player;
import com.example.rpsapi.api.model.entities.PlayerMove;

// TODO -> MAKE RECORD ==> HIBERNATE PROBLEM --> MISSING NO-ARG CONSTRUCTOR and SETTERS (SINCE IMMUTABLE)
// TODO -> NAMING
// TODO -> https://stackoverflow.com/questions/70403180/is-it-ok-to-use-java-records-with-service-restcontroller-annotations
public record GameCreatedState(Player playerOne) implements GameState {

    private static final String PLAYER_TWO_NAME_SUFFIX = "2";

    @Override
    public GameState joinGame(Player playerTwo) {

        if (isNameEqualToPlayerOneName(playerTwo)) {
            playerTwo = getNewPlayerTwoWithNameSuffix(playerTwo);
        }

        return new GameReadyState(playerOne, playerTwo); // Transition to GameReadyState
    }

    private boolean isNameEqualToPlayerOneName(Player playerTwo) {
        return playerOne.name().equals(playerTwo.name());
    }

    private Player getNewPlayerTwoWithNameSuffix(Player playerTwo) {
        return new Player(playerTwo.name() + PLAYER_TWO_NAME_SUFFIX); // ENFORCING IMMUTABILITY
    }

    @Override
    public GameState makeMove(PlayerMove playerMove) {
        throw new IllegalStateException("Game not full. Cannot make move");
    }
}