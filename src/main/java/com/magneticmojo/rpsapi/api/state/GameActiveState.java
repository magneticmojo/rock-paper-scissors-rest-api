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

    private static final int PLAYER_WITH_FIRST_MOVE_WINS_CONDITION = 1; // TODO -> FLYTTA IN I METOD
    private static final int PLAYER_WITH_LAST_MOVE_WINS_CONDITION = 2; // TODO -> FLYTTA IN I METOD

    @Override
    public GameState joinGame(Player player) {
        throw new MaxPlayerLimitReachedException("Game full. Cannot join game");
    }

    @Override
    public GameState makeMove(PlayerMove lastPlayerMove) { // TODO -> SÄKERSTÄLL EDGE CASES

        if (playerNotInGame(lastPlayerMove.player().name())) {
            throw new PlayerException("Player not in game. Cannot make move");
        }

        if (playerHasMadeMove(lastPlayerMove)) {
            throw new PlayerException("Player already made move. Cannot make move");
        }

        return new GameEndedState(playerOne, playerTwo, firstPlayerMove, lastPlayerMove, getResult(firstPlayerMove, lastPlayerMove));
    }

    private boolean playerNotInGame(String playerName) {
        return !playerOne.name().equals(playerName) && !playerTwo.name().equals(playerName);
    }

    private boolean playerHasMadeMove(PlayerMove lastPlayerMove) {
        return lastPlayerMove.player().name().equals(firstPlayerMove.player().name());
    }

    public String getResult(PlayerMove firstPlayerMove, PlayerMove lastPlayerMove) { // TODO SEPARATION OF CONCERNS --> TILL MOVE
        Player firstMovePlayer = firstPlayerMove.player();
        Player lastMovePlayer = lastPlayerMove.player();
        Move firstMove = firstPlayerMove.move();
        Move lastMove = lastPlayerMove.move();

        int relativePosition = calculateCyclicEnumRelation(firstMove, lastMove);

        if (PLAYER_WITH_FIRST_MOVE_WINS_CONDITION == relativePosition) {
            return generateVictoryMessage(firstMovePlayer, lastMovePlayer,firstMove, lastMove);
        } else if (PLAYER_WITH_LAST_MOVE_WINS_CONDITION == relativePosition) {
            return generateVictoryMessage(lastMovePlayer, firstMovePlayer,lastMove, firstMove);
        } else {
            return "TIE";
        }
    }

    private String generateVictoryMessage(Player player, Player opponent, Move ownMove, Move opponentMove) {
        return player.name() + " WON BY " + ownMove.name() + " BEATING " + opponentMove.name() + ". " + opponent.name() + " LOST";
    }

    private int calculateCyclicEnumRelation(Move firstMove, Move lastMove) { // todo -> MOVE TO ENUM
        int numberOfPossibleMoves = Move.values().length;
        return (numberOfPossibleMoves + firstMove.ordinal() - lastMove.ordinal()) % numberOfPossibleMoves;
    }
}
