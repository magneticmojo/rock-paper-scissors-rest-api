package com.magneticmojo.rpsapi.api.state;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rpsapi.api.exception.MaxPlayerLimitReachedException;
import com.magneticmojo.rpsapi.api.exception.PlayerException;
import com.magneticmojo.rpsapi.api.model.entities.Move;
import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import com.magneticmojo.rpsapi.api.serialization.GameActiveStateSerializer;

@JsonSerialize(using = GameActiveStateSerializer.class)
public record GameActiveState(Player playerOne,
                              Player playerTwo,
                              PlayerMove firstPlayerMove) implements GameState {

    @Override
    public GameState joinGame(Player player) {
        throw new MaxPlayerLimitReachedException("Game full. Cannot join game");
    }

    @Override
    public GameState makeMove(PlayerMove lastPlayerMove) {

        if (playerNotInGame(lastPlayerMove.player().name())) {
            throw new PlayerException("Player not in game. Cannot make move");
        }

        if (playerHasMadeMove(lastPlayerMove)) {
            throw new PlayerException("Player already made move. Cannot make move");
        }

        String result = getResult(firstPlayerMove, lastPlayerMove);

        return new GameEndedState(playerOne, playerTwo, firstPlayerMove, lastPlayerMove, result);
    }

    private boolean playerNotInGame(String playerName) {
        return !playerOne.name().equals(playerName) && !playerTwo.name().equals(playerName);
    }

    private boolean playerHasMadeMove(PlayerMove lastPlayerMove) {
        return lastPlayerMove.player().name().equals(firstPlayerMove.player().name());
    }

    public String getResult(PlayerMove firstPlayerMove, PlayerMove lastPlayerMove) {
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
        return player.name() + " WON BY " + ownMove.name() + " BEATING " + opponentMove.name() + ". " + opponent.name() + " LOST"; // TODO -> FLYTTA IN I Serializer
    }
}
