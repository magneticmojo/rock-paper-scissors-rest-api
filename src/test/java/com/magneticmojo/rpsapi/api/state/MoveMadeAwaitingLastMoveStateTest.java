package com.magneticmojo.rpsapi.api.state;

import com.magneticmojo.rpsapi.api.exceptions.gameexception.GameFullException;
import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import com.magneticmojo.rpsapi.api.model.entities.Move;
import com.magneticmojo.rpsapi.api.exceptions.playerexception.PlayerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveMadeAwaitingLastMoveStateTest {

    private Player playerOne;
    private Player playerTwo;
    private PlayerMove firstPlayerMove;
    private MoveMadeAwaitingLastMoveState moveMadeAwaitingLastMoveState;

    @BeforeEach
    void setUp() {
        playerOne = new Player("player1");
        playerTwo = new Player("player2");
        firstPlayerMove = new PlayerMove(playerOne, Move.ROCK);
        moveMadeAwaitingLastMoveState = new MoveMadeAwaitingLastMoveState(playerOne, playerTwo, firstPlayerMove);
    }

    @Test
    void testJoinGameException() {
        Player playerThree = new Player("player3");
        assertThrows(GameFullException.class, () -> moveMadeAwaitingLastMoveState.joinGame(playerThree));
    }

    @Test
    void testMakeMoveWithPlayerNotInGame() {
        Player playerThree = new Player("player3");
        PlayerMove playerThreeMove = new PlayerMove(playerThree, Move.ROCK);
        assertThrows(PlayerException.class, () -> moveMadeAwaitingLastMoveState.makeMove(playerThreeMove));
    }

    @Test
    void testMakeMoveWithPlayerAlreadyMadeMove() {
        PlayerMove duplicatePlayerMove = new PlayerMove(playerOne, Move.PAPER);
        assertThrows(PlayerException.class, () -> moveMadeAwaitingLastMoveState.makeMove(duplicatePlayerMove));
    }

/*    @Test
    void testMakeMoveWithValidMove() {
        PlayerMove validPlayerMove = new PlayerMove(playerTwo, Move.SCISSORS);
        GameState result = moveMadeAwaitingLastMoveState.makeMove(validPlayerMove);

        assertTrue(result instanceof GameEndedState);
        assertEquals("player1 WON BY ROCK BEATING SCISSORS. player2 LOST", ((GameEndedState) result).getResult());
    }

    @Test
    void testMakeMoveWithTie() {
        PlayerMove tiePlayerMove = new PlayerMove(playerTwo, Move.ROCK);
        GameState result = moveMadeAwaitingLastMoveState.makeMove(tiePlayerMove);

        assertTrue(result instanceof GameEndedState);
        assertEquals("TIE", ((GameEndedState) result).getResult());
    }*/
}
