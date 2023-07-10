package com.magneticmojo.rockpaperscissors.api.service.game.states;

import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.MissingPlayerTwoException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Move;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.RockPaperScissorsGameState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.PlayerOneJoinedState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.PlayerTwoJoinedState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PlayerOneJoinedState class.
 * <p>
 * These tests validate the behavior of the PlayerOneJoinedState class,
 * specifically testing the joining of a player to the game and making
 * moves when only one player has joined.
 */
public class PlayerOneJoinedStateTest {

    // ************************************* JOIN GAME *************************************

    @Test
    void joinGame_withUniquePlayerName_returnsPlayerTwoJoinedState() {
        PlayerOneJoinedState state = new PlayerOneJoinedState(new Player("p1"));
        Player playerTwo = new Player("p2");

        RockPaperScissorsGameState nextState = state.joinGame(playerTwo);

        assertTrue(nextState instanceof PlayerTwoJoinedState);
    }

    @Test
    void joinGame_withDuplicatePlayerName_appendsSuffixToPlayerTwoName() {
        PlayerOneJoinedState state = new PlayerOneJoinedState(new Player("p1"));
        Player playerTwo = new Player("p1");

        RockPaperScissorsGameState nextState = state.joinGame(playerTwo);

        assertTrue(nextState instanceof PlayerTwoJoinedState);
        assertEquals("p12", ((PlayerTwoJoinedState) nextState).getPlayerTwo().name());
    }

    // ************************************* MAKE MOVE *************************************

    @Test
    void makeMove_throwsGameNotFullException() {
        PlayerOneJoinedState state = new PlayerOneJoinedState(new Player("p1"));
        PlayerMove move = new PlayerMove(new Player("p1"), Move.ROCK);

        assertThrows(MissingPlayerTwoException.class, () -> state.makeMove(move));
    }
}
