package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states;

import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;

/**
 * The RockPaperScissorsGameState interface defines the methods to manage state transitions in a Rock-Paper-Scissors game.
 * Concrete state classes implement this interface to provide specific behavior for joining the game and making moves.
 */
public interface RockPaperScissorsGameState {

    RockPaperScissorsGameState joinGame(Player player);

    RockPaperScissorsGameState makeMove(PlayerMove playerMove);

}
