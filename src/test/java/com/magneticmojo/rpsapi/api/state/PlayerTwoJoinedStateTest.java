package com.magneticmojo.rpsapi.api.state;

import com.magneticmojo.rpsapi.api.exceptions.gameexception.GameFullException;
import com.magneticmojo.rpsapi.api.exceptions.playerexception.PlayerNotInGameException;
import com.magneticmojo.rpsapi.api.model.entities.Move;
import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTwoJoinedStateTest {

    @Test
    void joinGame_throwsGameFullException() {
        PlayerTwoJoinedState state = new PlayerTwoJoinedState(new Player("p1"), new Player("p2"));
        Player playerThree = new Player("p3");

        assertThrows(GameFullException.class, () -> state.joinGame(playerThree));
    }

    @Test
    void makeMove_withPlayerInGame_returnsFirstMoveMadeAwaitingLastMoveState() {
        PlayerTwoJoinedState state = new PlayerTwoJoinedState(new Player("p1"), new Player("p2"));
        PlayerMove move = new PlayerMove(new Player("p1"), Move.ROCK);

        GameState nextState = state.makeMove(move);

        assertTrue(nextState instanceof FirstMoveMadeAwaitingLastMoveState);
    }

    @Test
    void makeMove_withPlayerNotInGame_throwsPlayerNotInGameException() {
        PlayerTwoJoinedState state = new PlayerTwoJoinedState(new Player("p1"), new Player("p2"));
        PlayerMove move = new PlayerMove(new Player("p3"), Move.ROCK);

        assertThrows(PlayerNotInGameException.class, () -> state.makeMove(move));
    }
}
