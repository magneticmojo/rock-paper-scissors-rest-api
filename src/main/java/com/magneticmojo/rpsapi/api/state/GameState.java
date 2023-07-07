package com.magneticmojo.rpsapi.api.state;

import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;

/**
 * The GameState interface defines the methods to manage state transitions in a Rock-Paper-Scissors game.
 * Concrete state classes implement this interface to provide specific behavior for joining the game and making moves.
 */
public interface GameState {

    GameState joinGame(Player player);

    GameState makeMove(PlayerMove playerMove);

}
