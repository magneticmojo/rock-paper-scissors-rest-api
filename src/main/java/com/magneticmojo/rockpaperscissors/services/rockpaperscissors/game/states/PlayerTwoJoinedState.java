package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.GameFullException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.PlayerNotInGameException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;
import com.magneticmojo.rockpaperscissors.api.serialization.PlayerTwoJoinedStateSerializer;

/**
 * The PlayerTwoJoinedState class represents the game state when both players have joined a Rock-Paper-Scissors game.
 * It prohibits additional players from joining, and allows the first move to be made.
 * PlayerNotInGameException is thrown if the move is attempted by a player not in the game.
 * The class implements the RockPaperScissorsGameState interface, thus representing a concrete state of the State Pattern.
 */
@JsonSerialize(using = PlayerTwoJoinedStateSerializer.class)
public class PlayerTwoJoinedState implements RockPaperScissorsGameState {

    private final Player playerOne;
    private final Player playerTwo;

    public PlayerTwoJoinedState(Player playerOne, Player playerTwo) {
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
    public RockPaperScissorsGameState joinGame(Player player) {
        throw new GameFullException("Game full. Cannot join game");
    }

    @Override
    public RockPaperScissorsGameState makeMove(PlayerMove firstPlayerMove) {

        if (playerNotInGame(firstPlayerMove.player().name())) {
            throw new PlayerNotInGameException("Player not in game. Cannot make move");
        }

        return new FirstMoveMadeState(playerOne, playerTwo, firstPlayerMove);
    }

    private boolean playerNotInGame(String playerName) {
        return !playerOne.name().equals(playerName) && !playerTwo.name().equals(playerName);
    }
}
