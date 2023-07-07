package com.magneticmojo.rpsapi.api.state;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rpsapi.api.exceptions.gameexception.GameFullException;
import com.magneticmojo.rpsapi.api.exceptions.playerexception.PlayerNotInGameException;
import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import com.magneticmojo.rpsapi.api.serialization.PlayerTwoJoinedStateSerializer;

/**
 * The PlayerTwoJoinedState class represents the game state when both players have joined a Rock-Paper-Scissors game.
 * It prohibits additional players from joining, and allows the first move to be made.
 * PlayerNotInGameException is thrown if the move is attempted by a player not in the game.
 * The class implements the GameState interface, thus representing a concrete state of the State Pattern.
 */
@JsonSerialize(using = PlayerTwoJoinedStateSerializer.class)
public record PlayerTwoJoinedState(Player playerOne,
                                   Player playerTwo) implements GameState {

    @Override
    public GameState joinGame(Player player) {
        throw new GameFullException("Game full. Cannot join game");
    }

    @Override
    public GameState makeMove(PlayerMove firstPlayerMove) {

        if (playerNotInGame(firstPlayerMove.player().name())) {
            throw new PlayerNotInGameException("Player not in game. Cannot make move");
        }

        return new FirstMoveMadeAwaitingLastMoveState(playerOne, playerTwo, firstPlayerMove);
    }

    private boolean playerNotInGame(String playerName) {
        return !playerOne.name().equals(playerName) && !playerTwo.name().equals(playerName);
    }
}
