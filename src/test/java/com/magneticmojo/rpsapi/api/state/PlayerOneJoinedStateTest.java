package com.magneticmojo.rpsapi.api.state;

import com.magneticmojo.rpsapi.api.exceptions.gameexception.GameNotFullException;
import com.magneticmojo.rpsapi.api.model.entities.Move;
import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerOneJoinedStateTest {

    @Test
    void joinGame_withUniquePlayerName_returnsPlayerTwoJoinedState() {
        PlayerOneJoinedState state = new PlayerOneJoinedState(new Player("p1"));
        Player playerTwo = new Player("p2");

        GameState nextState = state.joinGame(playerTwo);

        assertTrue(nextState instanceof PlayerTwoJoinedState);
    }

    @Test
    void joinGame_withDuplicatePlayerName_appendsSuffixToPlayerTwoName() {
        PlayerOneJoinedState state = new PlayerOneJoinedState(new Player("p1"));
        Player playerTwo = new Player("p1");

        GameState nextState = state.joinGame(playerTwo);

        assertTrue(nextState instanceof PlayerTwoJoinedState);
        assertEquals("p12", ((PlayerTwoJoinedState) nextState).playerTwo().name());
    }

    @Test
    void makeMove_throwsGameNotFullException() {
        PlayerOneJoinedState state = new PlayerOneJoinedState(new Player("p1"));
        PlayerMove move = new PlayerMove(new Player("p1"), Move.ROCK);

        assertThrows(GameNotFullException.class, () -> state.makeMove(move));
    }
}
