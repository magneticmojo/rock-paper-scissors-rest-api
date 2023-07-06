package com.magneticmojo.rpsapi.api.state;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rpsapi.api.exceptions.gameexception.GameFullException;
import com.magneticmojo.rpsapi.api.exceptions.playerexception.MultipleMovesProhibitedException;
import com.magneticmojo.rpsapi.api.exceptions.playerexception.PlayerNotInGameException;
import com.magneticmojo.rpsapi.api.model.entities.Move;
import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import com.magneticmojo.rpsapi.api.serialization.FirstMoveMadeAwaitingLastMoveStateSerializer;

@JsonSerialize(using = FirstMoveMadeAwaitingLastMoveStateSerializer.class)
public record FirstMoveMadeAwaitingLastMoveState(Player playerOne,
                                                 Player playerTwo,
                                                 PlayerMove firstPlayerMove) implements GameState { // TODO @TEST

    @Override
    public GameState joinGame(Player player) {
        throw new GameFullException("Game full. Cannot join game");
    }

    @Override
    public GameState makeMove(PlayerMove lastPlayerMove) {

        if (playerNotInGame(lastPlayerMove.player().name())) {
            throw new PlayerNotInGameException("Player not in game. Cannot make move");
        }

        if (playerHasMadeMove(lastPlayerMove)) {
            throw new MultipleMovesProhibitedException("Player already made move. Cannot make another move");
        }

        String gameResult = getGameResult(firstPlayerMove, lastPlayerMove);

        return new GameEndedState(playerOne, playerTwo, firstPlayerMove, lastPlayerMove, gameResult);
    }

    private boolean playerNotInGame(String playerName) {
        return !playerOne.name().equals(playerName) && !playerTwo.name().equals(playerName);
    }

    private boolean playerHasMadeMove(PlayerMove lastPlayerMove) {
        return lastPlayerMove.player().name().equals(firstPlayerMove.player().name());
    }

    public String getGameResult(PlayerMove firstPlayerMove, PlayerMove lastPlayerMove) {
        Move firstMove = firstPlayerMove.move();
        Move lastMove = lastPlayerMove.move();

        if (firstMove.isTied(lastMove)) {
            return "TIE";
        } else if (firstMove.isWinnerAgainst(lastMove)) {
            return generateVictoryMessage(firstPlayerMove.player(), lastPlayerMove.player(), firstMove, lastMove);
        } else {
            return generateVictoryMessage(lastPlayerMove.player(), firstPlayerMove.player(),lastMove, firstMove);
        }
    }

    private String generateVictoryMessage(Player player, Player opponent, Move ownMove, Move opponentMove) {
        return player.name() + " won by " + ownMove.name() + " beating " + opponentMove.name() + ". " + opponent.name() + " lost";
    }
}
