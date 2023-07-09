package com.magneticmojo.rockpaperscissors.api.service.game.states;

import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.GameFullException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.MultipleMovesProhibitedException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.PlayerNotInGameException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Move;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.FirstMoveMadeState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.GameEndedState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.RockPaperScissorsGameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class validates the functionality of the FirstMoveMadeState class,
 * ensuring that the game state management works correctly after the first move is made in a game.
 * The tests cover various game scenarios including full games, player participation, and different move outcomes.
 */
class FirstMoveMadeStateTest {

    private Player playerOne;
    private Player playerTwo;
    private PlayerMove firstPlayerMove;
    private FirstMoveMadeState firstMoveMadeState;

    @BeforeEach
    void setUp() {
        playerOne = new Player("player1");
        playerTwo = new Player("player2");
        firstPlayerMove = new PlayerMove(playerOne, Move.ROCK);
        firstMoveMadeState = new FirstMoveMadeState(playerOne, playerTwo, firstPlayerMove);
    }

    @Test
    void testJoinGame_GameFullException() {
        Player playerThree = new Player("player3");
        assertThrows(GameFullException.class, () -> firstMoveMadeState.joinGame(playerThree));
    }

    @Test
    void testMakeMove_withPlayerNotInGame() {
        Player playerThree = new Player("player3");
        PlayerMove playerThreeMove = new PlayerMove(playerThree, Move.ROCK);
        assertThrows(PlayerNotInGameException.class, () -> firstMoveMadeState.makeMove(playerThreeMove));
    }

    @Test
    void testMakeMove_withPlayerAlreadyMadeMove() {
        PlayerMove duplicatePlayerMove = new PlayerMove(playerOne, Move.PAPER);
        assertThrows(MultipleMovesProhibitedException.class, () -> firstMoveMadeState.makeMove(duplicatePlayerMove));
    }

    @Test
    void testMakeMove_withValidMove() {
        PlayerMove validPlayerMove = new PlayerMove(playerTwo, Move.SCISSORS);
        RockPaperScissorsGameState gameResult = firstMoveMadeState.makeMove(validPlayerMove);

        assertTrue(gameResult instanceof GameEndedState);
        String expectedResult = "player1 won by ROCK beating SCISSORS. player2 lost";
        assertEquals(expectedResult, ((GameEndedState) gameResult).getGameResult());
    }

    @Test
    void testMakeMoveWithTie() {
        PlayerMove tiePlayerMove = new PlayerMove(playerTwo, Move.ROCK);
        RockPaperScissorsGameState gameResult = firstMoveMadeState.makeMove(tiePlayerMove);

        assertTrue(gameResult instanceof GameEndedState);
        String expectedResult = "TIE: ROCK vs ROCK";
        assertEquals(expectedResult, ((GameEndedState) gameResult).getGameResult());
    }
}
