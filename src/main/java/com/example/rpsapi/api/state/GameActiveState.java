package com.example.rpsapi.api.state;

import com.example.rpsapi.api.model.entities.Move;
import com.example.rpsapi.api.model.entities.Player;
import com.example.rpsapi.api.model.entities.PlayerMove;

public record GameActiveState(Player playerOne,
                              Player playerTwo,
                              PlayerMove firstPlayerMove) implements GameState {

    private static final int FIRST_MOVE_WINS_CONDITION = 1; // TODO BAD NAMING
    private static final int LAST_MOVE_WINS_CONDITION = 2;

    @Override
    public GameState joinGame(Player player) {
        throw new IllegalStateException("Game full. Cannot join game");
    }

    @Override
    public GameState makeMove(PlayerMove lastPlayerMove) { // TODO -> SÄKERSTÄLL EDGE CASES

        if (playerNotInGame(lastPlayerMove.player().name())) {
            throw new IllegalStateException("Player not in game. Cannot make move");
        }

        if (playerHasMadeMove(lastPlayerMove)) {
            throw new IllegalStateException("Player already made move. Cannot make move");
        }

        return new GameEndedState(playerOne, playerTwo, getResult(firstPlayerMove, lastPlayerMove));
    }

    private boolean playerNotInGame(String playerName) {
        return !playerOne.name().equals(playerName) && !playerTwo.name().equals(playerName);
    }

    private boolean playerHasMadeMove(PlayerMove lastPlayerMove) {
        return lastPlayerMove.player().name().equals(firstPlayerMove.player().name());
    }

    public String getResult(PlayerMove firstPlayerMove, PlayerMove lastPlayerMove) {
        Player firstMovePlayer = firstPlayerMove.player();
        Player lastMovePlayer = lastPlayerMove.player();
        Move firstMove = firstPlayerMove.move();
        Move lastMove = lastPlayerMove.move();

        int relativePosition = calculateCyclicEnumRelation(firstMove, lastMove);

        if (relativePosition == FIRST_MOVE_WINS_CONDITION) {
            return firstMovePlayer.name() + " WINS BY " + firstMove.name() + " BEATING " + lastMove.name();
        } else if (relativePosition == LAST_MOVE_WINS_CONDITION) {
            return lastMovePlayer.name() + " WINS BY " + lastMove.name() + " BEATING " + firstMove.name();
        } else {
            return "TIE";
        }
    }

    private int calculateCyclicEnumRelation(Move firstMove, Move lastMove) { // todo -> BAD NAMING
        int numberOfPossibleMoves = Move.values().length;
        return (numberOfPossibleMoves + firstMove.ordinal() - lastMove.ordinal()) % numberOfPossibleMoves;
    }
}
