package com.magneticmojo.rockpaperscissors.api.service.game.states;

import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.GameEndedException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Move;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.GameEndedState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GameEndedState class.
 * <p>
 * These tests validate the behavior of the GameEndedState class,
 * testing the handling of joining a game and making moves when the game has ended.
 */
public class GameEndedStateTest {

    private Player playerOne = new Player("p1");
    private Player playerTwo = new Player("p2");
    private Player playerThree = new Player("p3");
    private PlayerMove playerMoveOne = new PlayerMove(playerOne, Move.ROCK);
    private PlayerMove playerMoveTwo = new PlayerMove(playerTwo, Move.PAPER);
    private PlayerMove playerMoveThree = new PlayerMove(playerThree, Move.ROCK);

    private String gameResultPaperWin = playerTwo.name() + " won by " + Move.PAPER + " beating " + Move.ROCK + ". " + playerOne.name() + " lost";
    private String gameResultTie = "TIE: " + Move.ROCK + " vs " + Move.ROCK;

    private GameEndedState gameEndedStatePaperWin = new GameEndedState(playerOne, playerTwo, playerMoveOne, playerMoveTwo);

    private GameEndedState gameEndedStateTie = new GameEndedState(playerOne, playerTwo, playerMoveOne, playerMoveThree);


    @Test
    void testCannotJoinGame_throwsGameEndedException() {
        Player playerThree = new Player("p3");
        assertThrows(GameEndedException.class, () -> gameEndedStatePaperWin.joinGame(playerThree));
    }

    @Test
    void testCannotMakeMove_throwsGameEndedException() {
        PlayerMove move = new PlayerMove(new Player("p3"), Move.ROCK);
        assertThrows(GameEndedException.class, () -> gameEndedStatePaperWin.makeMove(move));
    }

    @Test
    void testPaperBeatsRock() {
        assertEquals(gameResultPaperWin, gameEndedStatePaperWin.getGameResult());
    }

    @Test
    void testTie() {
        assertEquals(gameResultTie, gameEndedStateTie.getGameResult());
    }
}
