package com.magneticmojo.rpsapi.api.state;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rpsapi.api.exception.JoinFullGameException;
import com.magneticmojo.rpsapi.api.exception.PlayerException;
import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import com.magneticmojo.rpsapi.api.serialization.GameReadyStateSerializer;

@JsonSerialize(using = GameReadyStateSerializer.class)
public record GameReadyState(Player playerOne,
                             Player playerTwo) implements GameState { // TODO @TEST

    @Override
    public GameState joinGame(Player player) {
        throw new JoinFullGameException("Game full. Cannot join game");
    }

    @Override
    public GameState makeMove(PlayerMove firstPlayerMove) {

        if (playerNotInGame(firstPlayerMove.player().name())) {
            throw new PlayerException("Player not in game. Cannot make move");
        }

        return new GameActiveState(playerOne, playerTwo, firstPlayerMove);
    }

    private boolean playerNotInGame(String playerName) {
        return !playerOne.name().equals(playerName) && !playerTwo.name().equals(playerName);
    }
}
