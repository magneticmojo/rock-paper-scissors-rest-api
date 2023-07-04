package com.example.rpsapi.api.state;

import com.example.rpsapi.api.model.entities.Move;
import com.example.rpsapi.api.model.entities.Player;

// TODO -> MAKE RECORD???
public class GameEndedState implements GameState {

    private final Player playerOne;
    private final Player playerTwo;
    private final String result;

    public GameEndedState(Player playerOne, Player playerTwo, String result) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.result = result;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public String getResult() {
        return result;
    }

    @Override
    public GameState joinGame(Player player) {
        throw new IllegalStateException("Game ended. Cannot join game");
    }

    @Override
    public GameState makeMove(String playerName, Move move) {
        throw new IllegalStateException("Game ended. Cannot make move");
    }

    @Override
    public String toString() {
        return "GameEndedState{" +
                "playerOne=" + playerOne +
                ", playerTwo=" + playerTwo +
                ", result='" + result + '\'' +
                '}';
    }
}
