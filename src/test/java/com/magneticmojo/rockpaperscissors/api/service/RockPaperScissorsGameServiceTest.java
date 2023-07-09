package com.magneticmojo.rockpaperscissors.api.service;

import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.RockPaperScissorsGameService;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.RockPaperScissorsGame;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.RockPaperScissorsGameState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.repository.InMemoryRockPaperScissorsGameRepository;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.exceptions.GameNotFoundException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Move;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This test class validates the functionality of the RockPaperScissorsGameService class,
 * ensuring that the service interacts correctly with InMemoryRockPaperScissorsGameRepository and RockPaperScissorsGame.
 * The tests cover scenarios like creating games, getting game states, joining games, and making moves,
 * while handling both success and failure scenarios.
 */
public class RockPaperScissorsGameServiceTest {

    private InMemoryRockPaperScissorsGameRepository inMemoryRockPaperScissorsGameRepository;
    private RockPaperScissorsGameService rockPaperScissorsGameService;
    private static final String TEST_GAME_ID = "d75bceed-c780-4f66-9a34-8da792db0976";

    @BeforeEach
    void setUp() {
        inMemoryRockPaperScissorsGameRepository = Mockito.mock(InMemoryRockPaperScissorsGameRepository.class);
        rockPaperScissorsGameService = new RockPaperScissorsGameService(inMemoryRockPaperScissorsGameRepository);
    }

    @Test
    void testCreateGame_returnsNonNullId_andCallsAddGame() {
        Player playerOne = new Player("playerOne");
        doNothing().when(inMemoryRockPaperScissorsGameRepository).addGame(any(RockPaperScissorsGame.class));

        String id = rockPaperScissorsGameService.createGame(playerOne);
        assertNotNull(id);

        verify(inMemoryRockPaperScissorsGameRepository, times(1)).addGame(any(RockPaperScissorsGame.class));
    }

    @Test
    void testGetGameState_whenGameExists_thenGameStateReturned() {
        RockPaperScissorsGame mockGame = mock(RockPaperScissorsGame.class);
        RockPaperScissorsGameState mockState = mock(RockPaperScissorsGameState.class);
        when(inMemoryRockPaperScissorsGameRepository.getGame(anyString())).thenReturn(mockGame);
        when(mockGame.getState()).thenReturn(mockState);

        RockPaperScissorsGameState resultState = rockPaperScissorsGameService.getGameState(TEST_GAME_ID);

        assertEquals(mockState, resultState);
        verify(inMemoryRockPaperScissorsGameRepository, times(1)).getGame(TEST_GAME_ID);
        verify(mockGame, times(1)).getState();
    }

    @Test
    void testJoinGame_whenGameExists_thenGameStateReturned() {
        RockPaperScissorsGame mockGame = mock(RockPaperScissorsGame.class);
        RockPaperScissorsGameState mockState = mock(RockPaperScissorsGameState.class);
        Player mockPlayer = mock(Player.class);
        when(inMemoryRockPaperScissorsGameRepository.getGame(anyString())).thenReturn(mockGame);
        when(mockGame.getState()).thenReturn(mockState);

        rockPaperScissorsGameService.joinGame(TEST_GAME_ID, mockPlayer);

        verify(inMemoryRockPaperScissorsGameRepository, times(1)).getGame(TEST_GAME_ID);
        verify(mockGame, times(1)).joinGame(mockPlayer);
        verify(mockGame, times(1)).getState();

        RockPaperScissorsGameState resultState = rockPaperScissorsGameService.getGameState(TEST_GAME_ID);
        assertEquals(mockState, resultState);
    }

    @Test
    void testMakeMove_whenGameExists_thenGameStateReturned() {
        RockPaperScissorsGame mockGame = mock(RockPaperScissorsGame.class);
        RockPaperScissorsGameState mockState = mock(RockPaperScissorsGameState.class);
        PlayerMove mockMove = mock(PlayerMove.class);
        when(inMemoryRockPaperScissorsGameRepository.getGame(anyString())).thenReturn(mockGame);
        when(mockGame.getState()).thenReturn(mockState);

        rockPaperScissorsGameService.makeMove(TEST_GAME_ID, mockMove);

        verify(inMemoryRockPaperScissorsGameRepository, times(1)).getGame(TEST_GAME_ID);
        verify(mockGame, times(1)).makeMove(mockMove);
        verify(mockGame, times(1)).getState();

        RockPaperScissorsGameState resultState = rockPaperScissorsGameService.getGameState(TEST_GAME_ID);
        assertEquals(mockState, resultState);
    }


    @Test
    void testGetGameState_whenGameDoesNotExist_thenThrowsGameNotFoundException() {
        when(inMemoryRockPaperScissorsGameRepository.getGame(anyString())).thenReturn(null);

        Exception exception = assertThrows(GameNotFoundException.class, () -> {
            rockPaperScissorsGameService.getGameState(TEST_GAME_ID);
        });

        assertEquals("Invalid id: " + TEST_GAME_ID, exception.getMessage());
    }

    @Test
    void testJoinGame_whenGameDoesNotExist_thenThrowsGameNotFoundException() {
        when(inMemoryRockPaperScissorsGameRepository.getGame(anyString())).thenReturn(null);

        Exception exception = assertThrows(GameNotFoundException.class, () -> {
            rockPaperScissorsGameService.joinGame(TEST_GAME_ID, new Player("somePlayer"));
        });

        assertEquals("Invalid id: " + TEST_GAME_ID, exception.getMessage());
    }

    @Test
    void testMakeMove_whenGameDoesNotExist_thenThrowsGameNotFoundException() {
        when(inMemoryRockPaperScissorsGameRepository.getGame(anyString())).thenReturn(null);

        Exception exception = assertThrows(GameNotFoundException.class, () -> {
            rockPaperScissorsGameService.makeMove(TEST_GAME_ID, new PlayerMove(new Player("somePlayer"), Move.PAPER));
        });

        assertEquals("Invalid id: " + TEST_GAME_ID, exception.getMessage());
    }
}