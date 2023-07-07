package com.magneticmojo.rpsapi.api.state;

import com.magneticmojo.rpsapi.api.exceptions.gameexception.GameFullException;
import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import com.magneticmojo.rpsapi.api.model.entities.Move;
import com.magneticmojo.rpsapi.api.exceptions.playerexception.PlayerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class validates the functionality of the FirstMoveMadeAwaitingLastMoveState class,
 * ensuring that the game state management works correctly after the first move is made in a game.
 * The tests cover various game scenarios including full games, player participation, and different move outcomes.
 */
class FirstMoveMadeAwaitingLastMoveStateTest {

    private Player playerOne;
    private Player playerTwo;
    private PlayerMove firstPlayerMove;
    private FirstMoveMadeAwaitingLastMoveState firstMoveMadeAwaitingLastMoveState;

    @BeforeEach
    void setUp() {
        playerOne = new Player("player1");
        playerTwo = new Player("player2");
        firstPlayerMove = new PlayerMove(playerOne, Move.ROCK);
        firstMoveMadeAwaitingLastMoveState = new FirstMoveMadeAwaitingLastMoveState(playerOne, playerTwo, firstPlayerMove);
    }

    @Test
    void testJoinGame_GameFullException() {
        Player playerThree = new Player("player3");
        assertThrows(GameFullException.class, () -> firstMoveMadeAwaitingLastMoveState.joinGame(playerThree));
    }

    @Test
    void testMakeMove_withPlayerNotInGame() {
        Player playerThree = new Player("player3");
        PlayerMove playerThreeMove = new PlayerMove(playerThree, Move.ROCK);
        assertThrows(PlayerException.class, () -> firstMoveMadeAwaitingLastMoveState.makeMove(playerThreeMove));
    }

    @Test
    void testMakeMove_withPlayerAlreadyMadeMove() {
        PlayerMove duplicatePlayerMove = new PlayerMove(playerOne, Move.PAPER);
        assertThrows(PlayerException.class, () -> firstMoveMadeAwaitingLastMoveState.makeMove(duplicatePlayerMove));
    }

    @Test
    void testMakeMove_withValidMove() {
        PlayerMove validPlayerMove = new PlayerMove(playerTwo, Move.SCISSORS);
        GameState gameResult = firstMoveMadeAwaitingLastMoveState.makeMove(validPlayerMove);

        assertTrue(gameResult instanceof GameEndedState);
        String expectedResult = "player1 won by ROCK beating SCISSORS. player2 lost";
        assertEquals(expectedResult, ((GameEndedState) gameResult).gameResult());
    }

    @Test
    void testMakeMoveWithTie() {
        PlayerMove tiePlayerMove = new PlayerMove(playerTwo, Move.ROCK);
        GameState gameResult = firstMoveMadeAwaitingLastMoveState.makeMove(tiePlayerMove);

        assertTrue(gameResult instanceof GameEndedState);
        String expectedResult = "TIE: ROCK vs ROCK";
        assertEquals(expectedResult, ((GameEndedState) gameResult).gameResult());
    }
}
