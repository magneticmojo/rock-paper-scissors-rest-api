package com.example.rpsapi.api.state;

import com.example.rpsapi.api.model.entities.Player;
import com.example.rpsapi.api.model.entities.PlayerMove;

public record GameEndedState(Player playerOne,
                             Player playerTwo,
                             String result) implements GameState {

    @Override
    public GameState joinGame(Player player) {
        throw new IllegalStateException("Game ended. Cannot join game");
    }

    @Override
    public GameState makeMove(PlayerMove playerMove) {
        throw new IllegalStateException("Game ended. Cannot make move");
    }
}
