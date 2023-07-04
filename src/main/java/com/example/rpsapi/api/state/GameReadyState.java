package com.example.rpsapi.api.state;

import com.example.rpsapi.api.model.entities.Move;
import com.example.rpsapi.api.model.entities.Player;

// TODO -> MAKE RECORD???
public class GameReadyState implements GameState {

    private final Player playerOne;
    private final Player playerTwo;

    public GameReadyState(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    @Override
    public GameState joinGame(Player player) {
        throw new IllegalStateException("Game full. Cannot join game");
    }

    @Override
    public GameState makeMove(String playerName, Move firstMove) {

        if (playerNotInGame(playerName)) {
            throw new IllegalStateException("Player not in game. Cannot make move");
        }

        if (playerOne.getName().equals(playerName)) {
            playerOne.setMove(firstMove);
        } else {
            playerTwo.setMove(firstMove);
        }

        return new GameActiveState(playerOne, playerTwo);
    }

    private boolean playerNotInGame(String playerName) {
        return !playerOne.getName().equals(playerName) && !playerTwo.getName().equals(playerName);
    }

    @Override
    public String toString() {
        return "GameReadyState{" +
                "playerOne=" + playerOne +
                ", playerTwo=" + playerTwo +
                '}';
    }
}
