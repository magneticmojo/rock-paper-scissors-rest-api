package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.GameEndedException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Move;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;
import com.magneticmojo.rockpaperscissors.api.serialization.gamestates.GameEndedStateSerializer;

/**
 * The GameEndedState class represents the final game state when a game of Rock-Paper-Scissors has concluded.
 * At this state, the game results are set, and no further operations like joining the game or making a move are allowed.
 * Any attempt to perform these operations will result in a GameEndedException.
 * The class implements the RockPaperScissorsGameState interface, thus representing a concrete state of the State Pattern.
 */
@JsonSerialize(using = GameEndedStateSerializer.class)
public class GameEndedState implements RockPaperScissorsGameState {

    private final Player playerOne;
    private final Player playerTwo;
    private final PlayerMove firstPlayerMove;
    private final PlayerMove lastPlayerMove;
    private final String gameResult;

    public GameEndedState(Player playerOne, Player playerTwo, PlayerMove firstPlayerMove, PlayerMove lastPlayerMove) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.firstPlayerMove = firstPlayerMove;
        this.lastPlayerMove = lastPlayerMove;
        this.gameResult = determineGameResult(firstPlayerMove, lastPlayerMove);
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

    public PlayerMove getLastPlayerMove() {
        return lastPlayerMove;
    }

    public String getGameResult() {
        return gameResult;
    }

    public String determineGameResult(PlayerMove firstPlayerMove, PlayerMove lastPlayerMove) {
        Move firstMove = firstPlayerMove.move();
        Move lastMove = lastPlayerMove.move();

        if (firstMove.isTied(lastMove)) {
            return generateTieMessage(firstMove, lastMove);
        } else if (firstMove.isWinnerAgainst(lastMove)) {
            return generateVictoryMessage(firstPlayerMove.player(), lastPlayerMove.player(), firstMove, lastMove);
        } else {
            return generateVictoryMessage(lastPlayerMove.player(), firstPlayerMove.player(), lastMove, firstMove);
        }
    }

    private String generateVictoryMessage(Player player, Player opponent, Move ownMove, Move opponentMove) {
        return player.name() + " won by " + ownMove.name() + " beating " + opponentMove.name() + ". " + opponent.name() + " lost";
    }

    private String generateTieMessage(Move ownMove, Move opponentMove) {
        return "TIE: " + ownMove.name() + " vs " + opponentMove.name();
    }

    @Override
    public RockPaperScissorsGameState joinGame(Player player) {
        throw new GameEndedException("Game ended. Cannot join game");
    }

    @Override
    public RockPaperScissorsGameState makeMove(PlayerMove playerMove) {
        throw new GameEndedException("Game ended. Cannot make move");
    }
}
