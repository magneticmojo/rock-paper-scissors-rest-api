package com.magneticmojo.rpsapi.api.service;

import com.magneticmojo.rpsapi.api.exceptions.gameexception.GameNotFoundException;
import com.magneticmojo.rpsapi.api.model.entities.Move;
import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import com.magneticmojo.rpsapi.api.model.entities.RockPaperScissorsGame;
import com.magneticmojo.rpsapi.api.repository.GameRepository;
import com.magneticmojo.rpsapi.api.state.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameServiceTest {

    private GameRepository gameRepository;
    private GameService gameService;
    private static final String TEST_GAME_ID = "d75bceed-c780-4f66-9a34-8da792db0976";

    @BeforeEach
    void setUp() {
        gameRepository = Mockito.mock(GameRepository.class);
        gameService = new GameService(gameRepository);
    }

    @Test
    void testCreateGame_returnsNonNullId_andCallsAddGame() {
        Player playerOne = new Player("playerOne");
        doNothing().when(gameRepository).addGame(any(RockPaperScissorsGame.class));

        String id = gameService.createGame(playerOne);
        assertNotNull(id);

        verify(gameRepository, times(1)).addGame(any(RockPaperScissorsGame.class));
    }

    @Test
    void testGetGameState_whenGameExists_thenGameStateReturned() {
        RockPaperScissorsGame mockGame = mock(RockPaperScissorsGame.class);
        GameState mockState = mock(GameState.class);
        when(gameRepository.getGame(anyString())).thenReturn(mockGame);
        when(mockGame.getState()).thenReturn(mockState);

        GameState resultState = gameService.getGameState(TEST_GAME_ID);

        assertEquals(mockState, resultState);
        verify(gameRepository, times(1)).getGame(TEST_GAME_ID);
        verify(mockGame, times(1)).getState();
    }

    @Test
    void testJoinGame_whenGameExists_thenGameStateReturned() {
        RockPaperScissorsGame mockGame = mock(RockPaperScissorsGame.class);
        GameState mockState = mock(GameState.class);
        Player mockPlayer = mock(Player.class);
        when(gameRepository.getGame(anyString())).thenReturn(mockGame);
        when(mockGame.joinGame(mockPlayer)).thenReturn(mockState);

        GameState resultState = gameService.joinGame(TEST_GAME_ID, mockPlayer);

        assertEquals(mockState, resultState);
        verify(gameRepository, times(1)).getGame(TEST_GAME_ID);
        verify(mockGame, times(1)).joinGame(mockPlayer);
    }

    @Test
    void testMakeMove_whenGameExists_thenGameStateReturned() {
        RockPaperScissorsGame mockGame = mock(RockPaperScissorsGame.class);
        GameState mockState = mock(GameState.class);
        PlayerMove mockMove = mock(PlayerMove.class);
        when(gameRepository.getGame(anyString())).thenReturn(mockGame);
        when(mockGame.makeMove(mockMove)).thenReturn(mockState);

        GameState resultState = gameService.makeMove(TEST_GAME_ID, mockMove);

        assertEquals(mockState, resultState);
        verify(gameRepository, times(1)).getGame(TEST_GAME_ID);
        verify(mockGame, times(1)).makeMove(mockMove);
    }

    @Test
    void testGetGameState_whenGameDoesNotExist_thenThrowsGameNotFoundException() {
        when(gameRepository.getGame(anyString())).thenReturn(null);

        Exception exception = assertThrows(GameNotFoundException.class, () -> {
            gameService.getGameState(TEST_GAME_ID);
        });

        assertEquals("Invalid id: " + TEST_GAME_ID, exception.getMessage());
    }

    @Test
    void testJoinGame_whenGameDoesNotExist_thenThrowsGameNotFoundException() {
        when(gameRepository.getGame(anyString())).thenReturn(null);

        Exception exception = assertThrows(GameNotFoundException.class, () -> {
            gameService.joinGame(TEST_GAME_ID, new Player("somePlayer"));
        });

        assertEquals("Invalid id: " + TEST_GAME_ID, exception.getMessage());
    }

    @Test
    void testMakeMove_whenGameDoesNotExist_thenThrowsGameNotFoundException() {
        when(gameRepository.getGame(anyString())).thenReturn(null);

        Exception exception = assertThrows(GameNotFoundException.class, () -> {
            gameService.makeMove(TEST_GAME_ID, new PlayerMove(new Player("somePlayer"), Move.PAPER));
        });

        assertEquals("Invalid id: " + TEST_GAME_ID, exception.getMessage());
    }
}