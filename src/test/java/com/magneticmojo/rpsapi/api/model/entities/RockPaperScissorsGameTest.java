package com.magneticmojo.rpsapi.api.model.entities;

import com.magneticmojo.rpsapi.api.exceptions.gameexception.GameNotFullException;
import com.magneticmojo.rpsapi.api.state.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RockPaperScissorsGameTest {

    private Player p1;
    private Player p2;
    private RockPaperScissorsGame game;

    @BeforeEach
    void setUp() {
        p1 = new Player("p1");
        p2 = new Player("p2");
        game = new RockPaperScissorsGame(p1);
    }

    @Test
    void testJoinGameAndMakeMove_transitionsToNextState_notEqualsPreviousState() {
        GameState state1 = game.getState();
        assertEquals(state1.getClass(), PlayerOneJoinedState.class);

        GameState state2 = game.joinGame(p2);
        assertEquals(state2.getClass(), PlayerTwoJoinedState.class);
        assertNotEquals(state1, state2);

        PlayerMove playerOneMove = new PlayerMove(p1, Move.ROCK);
        GameState state3 = game.makeMove(playerOneMove);
        assertEquals(state3.getClass(), FirstMoveMadeAwaitingLastMoveState.class);
        assertNotEquals(state2, state3);

        PlayerMove playerTwoMove = new PlayerMove(p2, Move.ROCK);
        GameState state4 = game.makeMove(playerTwoMove);
        assertNotEquals(state3, state4);
        assertEquals(state4.getClass(), GameEndedState.class);
    }

    @Test
    void testStateTransition_DoNotOccur_AfterDeniedRequest() {
        GameState state1 = game.getState();
        PlayerMove playerOneMove = new PlayerMove(p1, Move.ROCK);
        assertThrows(GameNotFullException.class, () -> game.makeMove(playerOneMove));
        assertEquals(state1.getClass(), PlayerOneJoinedState.class);
    }

    @Test
    void testGameFlowResultsInTie() {
        game.joinGame(p2);

        PlayerMove playerOneMove = new PlayerMove(p1, Move.ROCK);
        game.makeMove(playerOneMove);

        PlayerMove playerTwoMove = new PlayerMove(p2, Move.ROCK);
        GameState state = game.makeMove(playerTwoMove);
        assertEquals(state.getClass(), GameEndedState.class);
        assertEquals("TIE", ((GameEndedState) state).gameResult());

    }

    @Test
    void testGameFlowResultsInPLayerTwoWins() {
        game.joinGame(p2);

        PlayerMove playerOneMove = new PlayerMove(p1, Move.ROCK);
        game.makeMove(playerOneMove);

        PlayerMove playerTwoMove = new PlayerMove(p2, Move.PAPER);
        GameState state = game.makeMove(playerTwoMove);

        String gameResult = p2.name() + " won by " + Move.PAPER + " beating " + Move.ROCK + ". " + p1.name() + " lost";
        assertEquals(state.getClass(), GameEndedState.class);
        assertEquals(gameResult, ((GameEndedState) state).gameResult());
    }
}
