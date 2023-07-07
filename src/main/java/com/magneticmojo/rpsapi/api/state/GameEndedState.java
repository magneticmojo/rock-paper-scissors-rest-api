package com.magneticmojo.rpsapi.api.state;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rpsapi.api.exceptions.gameexception.GameEndedException;
import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import com.magneticmojo.rpsapi.api.serialization.GameEndedStateSerializer;

/**
 * The GameEndedState class represents the final game state when a game of Rock-Paper-Scissors has concluded.
 * At this state, the game results are set, and no further operations like joining the game or making a move are allowed.
 * Any attempt to perform these operations will result in a GameEndedException.
 * The class implements the GameState interface, thus representing a concrete state of the State Pattern.
 */
@JsonSerialize(using = GameEndedStateSerializer.class)
public record GameEndedState(Player playerOne,
                             Player playerTwo,
                             PlayerMove firstPlayerMove,
                             PlayerMove lastPlayerMove,
                             String gameResult) implements GameState {

    @Override
    public GameState joinGame(Player player) {
        throw new GameEndedException("Game ended. Cannot join game");
    }

    @Override
    public GameState makeMove(PlayerMove playerMove) {
        throw new GameEndedException("Game ended. Cannot make move");
    }
}
