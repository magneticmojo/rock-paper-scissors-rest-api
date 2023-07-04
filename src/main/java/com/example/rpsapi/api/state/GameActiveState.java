package com.example.rpsapi.api.state;

import com.example.rpsapi.api.model.entities.Move;
import com.example.rpsapi.api.model.entities.Player;

// TODO -> MAKE RECORD???
public class GameActiveState implements GameState {

    private final Player playerOne;
    private final Player playerTwo;

    public GameActiveState(Player playerOne, Player playerTwo) {
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
    public GameState makeMove(String playerName, Move secondMove) {

        if (playerNotInGame(playerName)) {
            throw new IllegalStateException("Player not in game. Cannot make move");
        }

        // TODO --> Edge cases
        // name = playerOne && playerOne.hasMove() --> throw exception
        // name = playerTwo && playerTwo.hasMove() --> throw exception

        if (!playerOne.hasMove() && playerName.equals(playerOne.getName())) {
            playerOne.setMove(secondMove);
        } else if (!playerTwo.hasMove() && playerName.equals(playerTwo.getName())) {
            playerTwo.setMove(secondMove);
        } else {
            throw new IllegalStateException("Player already made move. Cannot make move");
        }

        return new GameEndedState(playerOne, playerTwo, getResult());
    }

    private boolean playerNotInGame(String playerName) {
        return !playerOne.getName().equals(playerName) && !playerTwo.getName().equals(playerName);
    }

    // TODO --> Fix ENUM + CALCULATION
    private String getResult() {
        Move.Result result = playerOne.getMove().against(playerTwo.getMove());

        String winner = "";

        switch (result) {
            case WIN -> winner = playerOne.getName();
            case LOSE -> winner = playerTwo.getName();
            case DRAW -> winner = "DRAW";
        }
        return winner;
    }

    @Override
    public String toString() {
        return "GameActiveState{" +
                "playerOne=" + playerOne +
                ", playerTwo=" + playerTwo +
                '}';
    }
}
