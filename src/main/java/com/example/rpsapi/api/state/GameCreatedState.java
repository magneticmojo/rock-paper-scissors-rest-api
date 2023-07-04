package com.example.rpsapi.api.state;

import com.example.rpsapi.api.model.entities.Move;
import com.example.rpsapi.api.model.entities.Player;

// TODO -> MAKE RECORD???
public class GameCreatedState implements GameState {

    private static final String PLAYER_TWO_NAME_SUFFIX = "2";
    private final Player playerOne;

    public GameCreatedState(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    @Override
    public GameState joinGame(Player playerTwo) {

        if (isDuplicateName(playerTwo)) {
            appendPlayerTwoNameSuffix(playerTwo);
        }

        System.out.println("Player 2: " + playerTwo.getName());

        return new GameReadyState(playerOne, playerTwo); // Transition to GameReadyState
    }

    private boolean isDuplicateName(Player playerTwo) {
        return playerOne.getName().equals(playerTwo.getName());
    }

    private void appendPlayerTwoNameSuffix(Player playerTwo) {
        playerTwo.setName(playerTwo.getName() + PLAYER_TWO_NAME_SUFFIX);
    }

    @Override
    public GameState makeMove(String playerName, Move move) {
        throw new IllegalStateException("Game not full. Cannot make move");
    }

    @Override
    public String toString() {
        return "GameCreatedState{" +
                "playerOne=" + playerOne +
                '}';
    }
}
