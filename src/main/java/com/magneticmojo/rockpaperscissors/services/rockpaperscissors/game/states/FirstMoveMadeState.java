package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.GameFullException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.MultipleMovesProhibitedException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.PlayerNotInGameException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;
import com.magneticmojo.rockpaperscissors.api.serialization.gamestates.FirstMoveMadeStateSerializer;

/**
 * The FirstMoveMadeState class represents the game state after the first player has made a move,
 * and the game is waiting for the second player's move. The class handles the game logic and checks for invalid operations.
 * Any further attempt to join the game, a player not in the game trying to make a move or player in the game trying to make a second move result in an exception.
 * The class implements the RockPaperScissorsGameState interface, thus representing a concrete state of the State Pattern.
 */
@JsonSerialize(using = FirstMoveMadeStateSerializer.class)
public class FirstMoveMadeState implements RockPaperScissorsGameState {

    private final Player playerOne;
    private final Player playerTwo;
    private final PlayerMove firstPlayerMove;

    public FirstMoveMadeState(Player playerOne, Player playerTwo, PlayerMove firstPlayerMove) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.firstPlayerMove = firstPlayerMove;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public PlayerMove getFirstPlayerMove() {
        return firstPlayerMove;
    }

    @Override
    public RockPaperScissorsGameState joinGame(Player player) {
        throw new GameFullException("Game full. Cannot join game");
    }

    @Override
    public RockPaperScissorsGameState makeMove(PlayerMove lastPlayerMove) {

        if (playerNotInGame(lastPlayerMove.player().name())) {
            throw new PlayerNotInGameException("Player not in game. Cannot make move");
        }

        if (playerHasMadeMove(lastPlayerMove)) {
            throw new MultipleMovesProhibitedException("Player already made move. Cannot make another move");
        }

        return new GameEndedState(playerOne, playerTwo, firstPlayerMove, lastPlayerMove);
    }

    private boolean playerNotInGame(String playerName) {
        return !playerOne.name().equals(playerName) && !playerTwo.name().equals(playerName);
    }

    private boolean playerHasMadeMove(PlayerMove lastPlayerMove) {
        return lastPlayerMove.player().name().equals(firstPlayerMove.player().name());
    }
}
