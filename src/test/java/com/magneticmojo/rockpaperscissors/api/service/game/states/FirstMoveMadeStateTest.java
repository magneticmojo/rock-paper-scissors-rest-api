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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class validates the functionality of the FirstMoveMadeState class,
 * ensuring that the game state management works correctly after the first move is made in a game.
 * The tests cover various game scenarios including full games, player participation, and different move outcomes.
 */
class FirstMoveMadeStateTest {

    private final Player playerOne = new Player("player1");
    private final Player playerTwo = new Player("player2");
    private final Player playerThree = new Player("player3");
    private final PlayerMove firstPlayerMove = new PlayerMove(playerOne, Move.ROCK);
    private final FirstMoveMadeState firstMoveMadeState = new FirstMoveMadeState(playerOne, playerTwo, firstPlayerMove);

    // *********************************** JOIN GAME ************************************************

    @Test
    void testJoinGame_GameFullException() {
        assertThrows(GameFullException.class, () -> firstMoveMadeState.joinGame(playerThree));
    }

    // *********************************** MAKE MOVE ************************************************

    @Test
    void testMakeMove_withPlayerNotInGame() {
        PlayerMove playerThreeMove = new PlayerMove(playerThree, Move.ROCK);
        assertThrows(PlayerNotInGameException.class, () -> firstMoveMadeState.makeMove(playerThreeMove));
    }

    @Test
    void testMakeMove_withPlayerAlreadyMadeMove() {
        PlayerMove duplicatePlayerMove = new PlayerMove(playerOne, Move.PAPER);
        assertThrows(MultipleMovesProhibitedException.class, () -> firstMoveMadeState.makeMove(duplicatePlayerMove));
    }

    @Test
    void testMakeMove_withValidMove_returnsGameEndedState() {
        PlayerMove validPlayerMove = new PlayerMove(playerTwo, Move.SCISSORS);
        RockPaperScissorsGameState gameState = firstMoveMadeState.makeMove(validPlayerMove);
        assertTrue(gameState instanceof GameEndedState);
    }
}
