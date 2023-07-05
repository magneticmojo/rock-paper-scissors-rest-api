package com.example.rpsapi.api.state;

import com.example.rpsapi.api.model.entities.Player;
import com.example.rpsapi.api.model.entities.PlayerMove;

// TODO -> RECORD WEAKENS ENCAPSULATION - BUT BOILERPLATE CODE IS REDUCED
public record GameReadyState(Player playerOne,
                             Player playerTwo) implements GameState {

    @Override
    public GameState joinGame(Player player) {
        throw new IllegalStateException("Game full. Cannot join game");
    }

    @Override
    public GameState makeMove(PlayerMove firstPlayerMove) {

        if (playerNotInGame(firstPlayerMove.player().name())) {
            throw new IllegalStateException("Player not in game. Cannot make move");
        }

        return new GameActiveState(playerOne, playerTwo, firstPlayerMove);
    }

    private boolean playerNotInGame(String playerName) {
        return !playerOne.name().equals(playerName) && !playerTwo.name().equals(playerName);
    }
}
