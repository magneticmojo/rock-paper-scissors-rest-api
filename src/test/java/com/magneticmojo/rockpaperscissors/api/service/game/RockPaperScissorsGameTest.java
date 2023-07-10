package com.magneticmojo.rockpaperscissors.api.service.game;

import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.*;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Move;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.RockPaperScissorsGame;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a test class for the RockPaperScissorsGame class.
 * It tests the behavior of the game in different situations and verifies the correctness
 * of the game state transitions.
 * It covers scenarios such as joining the game, making a move, and the game result
 * in various scenarios such as a tie and a win by any player.
 * It also ensures the correctness of the game rules such as a player not in the game can't make a move,
 * no player can join a game that has ended or is already full.
 */
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
    public void testConstructor_withNullPlayer_throwsPlayerNullException() {
        assertThrows(PlayerNullException.class, () -> new RockPaperScissorsGame(null));
    }

    @Test
    void testJoinGameAndMakeMove_transitionsToNextState_notEqualsPreviousState() {
        RockPaperScissorsGameState state1 = game.getState();
        assertEquals(state1.getClass(), PlayerOneJoinedState.class);

        game.joinGame(p2);
        RockPaperScissorsGameState state2 = game.getState();

        assertEquals(state2.getClass(), PlayerTwoJoinedState.class);
        assertNotEquals(state1, state2);

        PlayerMove playerOneMove = new PlayerMove(p1, Move.ROCK);
        game.makeMove(playerOneMove);
        RockPaperScissorsGameState state3 = game.getState();
        assertEquals(state3.getClass(), FirstMoveMadeState.class);
        assertNotEquals(state2, state3);

        PlayerMove playerTwoMove = new PlayerMove(p2, Move.ROCK);
        game.makeMove(playerTwoMove);
        RockPaperScissorsGameState state4 = game.getState();

        assertNotEquals(state3, state4);
        assertEquals(state4.getClass(), GameEndedState.class);
    }

    @Test
    void testStateTransition_DoNotOccur_AfterDeniedRequest() {
        RockPaperScissorsGameState state1 = game.getState();
        PlayerMove playerOneMove = new PlayerMove(p1, Move.ROCK);
        assertThrows(MissingPlayerTwoException.class, () -> game.makeMove(playerOneMove));
        assertEquals(state1.getClass(), PlayerOneJoinedState.class);
    }

    @Test
    void testGameFlow_resultsInTie() {
        game.joinGame(p2);

        PlayerMove playerOneMove = new PlayerMove(p1, Move.ROCK);
        game.makeMove(playerOneMove);

        PlayerMove playerTwoMove = new PlayerMove(p2, Move.ROCK);
        game.makeMove(playerTwoMove);
        RockPaperScissorsGameState state = game.getState();

        String tie = "TIE: " + playerOneMove.move().name() + " vs " + playerTwoMove.move().name();

        assertEquals(state.getClass(), GameEndedState.class);
        assertEquals(tie, ((GameEndedState) state).getGameResult());

    }

    @Test
    void testGameFlow_resultsInPLayerTwoWins() {
        game.joinGame(p2);

        PlayerMove playerOneMove = new PlayerMove(p1, Move.ROCK);
        game.makeMove(playerOneMove);

        PlayerMove playerTwoMove = new PlayerMove(p2, Move.PAPER);
        game.makeMove(playerTwoMove);
        RockPaperScissorsGameState state = game.getState();

        String gameResult = p2.name() + " won by " + Move.PAPER + " beating " + Move.ROCK + ". " + p1.name() + " lost";
        assertEquals(state.getClass(), GameEndedState.class);
        assertEquals(gameResult, ((GameEndedState) state).getGameResult());
    }


    @Test
    void testJoinGame_AfterGameEnded_ThrowsGameEndedException() {
        game.joinGame(p2);

        PlayerMove playerOneMove = new PlayerMove(p1, Move.ROCK);
        game.makeMove(playerOneMove);

        PlayerMove playerTwoMove = new PlayerMove(p2, Move.ROCK);
        game.makeMove(playerTwoMove);

        Player p3 = new Player("p3");
        assertThrows(GameEndedException.class, () -> game.joinGame(p3));
    }

    @Test
    void testJoinGame_AfterGameFull_ThrowsGameFullException() {
        game.joinGame(p2);
        Player p3 = new Player("p3");
        assertThrows(GameFullException.class, () -> game.joinGame(p3));
    }

    @Test
    void testMakeMove_ByPlayerNotInGame_ThrowsPlayerNotInGameException() {
        game.joinGame(p2);
        Player p3 = new Player("p3");
        PlayerMove playerThreeMove = new PlayerMove(p3, Move.PAPER);
        assertThrows(PlayerNotInGameException.class, () -> game.makeMove(playerThreeMove));
    }
}
