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

    @Test
    void joinGame_throwsGameEndedException() {
        GameEndedState state = new GameEndedState(
                new Player("p1"),
                new Player("p2"),
                new PlayerMove(new Player("p1"), Move.ROCK),
                new PlayerMove(new Player("p2"), Move.PAPER));

        Player playerThree = new Player("p3");

        assertThrows(GameEndedException.class, () -> state.joinGame(playerThree));
    }

    @Test
    void makeMove_throwsGameEndedException() {
        GameEndedState state = new GameEndedState(
                new Player("p1"),
                new Player("p2"),
                new PlayerMove(new Player("p1"), Move.ROCK),
                new PlayerMove(new Player("p2"), Move.PAPER));

        PlayerMove move = new PlayerMove(new Player("p3"), Move.ROCK);

        assertThrows(GameEndedException.class, () -> state.makeMove(move));
    }
}
