package com.magneticmojo.rpsapi.api.state;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import com.magneticmojo.rpsapi.api.serialization.GameEndedStateSerializer;

@JsonSerialize(using = GameEndedStateSerializer.class)
public record GameEndedState(Player playerOne,
                             Player playerTwo,
                             PlayerMove firstPlayerMove,
                             PlayerMove lastPlayerMove,
                             String result) implements GameState { // TODO @TEST

    @Override
    public GameState joinGame(Player player) {
        throw new IllegalStateException("Game ended. Cannot join game");
    }

    @Override
    public GameState makeMove(PlayerMove playerMove) {
        throw new IllegalStateException("Game ended. Cannot make move");
    }
}
