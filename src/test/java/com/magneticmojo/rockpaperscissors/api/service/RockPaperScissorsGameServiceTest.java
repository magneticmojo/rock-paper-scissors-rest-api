package com.magneticmojo.rockpaperscissors.api.service;

import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.RockPaperScissorsGameService;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.PlayerMoveNullException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.PlayerNullException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.RockPaperScissorsGame;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.RockPaperScissorsGameState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.repository.InMemoryRockPaperScissorsGameRepository;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.exceptions.GameNotFoundException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Move;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This test class validates the functionality of the RockPaperScissorsGameService class,
 * ensuring that the service interacts correctly with InMemoryRockPaperScissorsGameRepository and RockPaperScissorsGame.
 * The tests cover scenarios like creating games, getting game states, joining games, and making moves,
 * while handling both success and failure scenarios.
 */
@ExtendWith(MockitoExtension.class)
public class RockPaperScissorsGameServiceTest {

    @Mock
    private InMemoryRockPaperScissorsGameRepository gameRepository;

    @InjectMocks
    private RockPaperScissorsGameService gameService;

    private final String gameId = "d75bceed-c780-4f66-9a34-8da792db0976";
    private Player playerOne = new Player("playerOne");
    private Player playerTwo = new Player("playerTwo");
    private PlayerMove playerMove = new PlayerMove(playerTwo, Move.PAPER);

    private String gameNotFoundMessage = "Invalid id: ";
    private String gameNotFoundError = "GAME_NOT_FOUND";
    private String playerNullMessage = "Player cannot be null";
    private String playerNullError = "PLAYER_NULL";
    private String playerMoveNullMessage = "PlayerMove cannot be null";
    private String playerMoveNullError = "PLAYER_MOVE_NULL";

    @BeforeEach
    void setUp() {
        gameRepository = Mockito.mock(InMemoryRockPaperScissorsGameRepository.class);
        gameService = new RockPaperScissorsGameService(gameRepository);
    }

    // ********************************************** CREATE GAME TESTS *************************************************

    @Test
    void testCreateGame_returnsNonNullId_andCallsAddGame() {
        doNothing().when(gameRepository).addGame(any(RockPaperScissorsGame.class));

        String id = gameService.createGame(playerOne);
        assertNotNull(id);

        verify(gameRepository, times(1)).addGame(any(RockPaperScissorsGame.class));
    }

    @Test
    void testCreateGame_whenPlayerIsNull_thenThrowsPlayerNullException() {
        PlayerNullException exception = assertThrows(PlayerNullException.class, () -> {
            gameService.createGame(null);
        });

        assertEquals(playerNullMessage, exception.getMessage());
        assertEquals(playerNullError, exception.getErrorCode());
    }

    // ********************************************** GET GAME STATE TESTS **********************************************

    @Test
    void testGetGameState_whenGameExists_thenGameStateReturned() {
        RockPaperScissorsGame mockGame = mock(RockPaperScissorsGame.class);
        RockPaperScissorsGameState mockState = mock(RockPaperScissorsGameState.class);
        when(gameRepository.getGame(anyString())).thenReturn(mockGame);
        when(mockGame.getState()).thenReturn(mockState);

        RockPaperScissorsGameState resultState = gameService.getGameState(gameId);

        assertEquals(mockState, resultState);
        verify(gameRepository, times(1)).getGame(gameId);
        verify(mockGame, times(1)).getState();
    }


    @Test
    void testGetGameState_whenGameDoesNotExist_thenThrowsGameNotFoundException() {
        when(gameRepository.getGame(anyString())).thenReturn(null);

        GameNotFoundException exception = assertThrows(GameNotFoundException.class, () -> {
            gameService.getGameState(gameId);
        });

        assertEquals(gameNotFoundMessage + gameId, exception.getMessage());
        assertEquals(gameNotFoundError, exception.getErrorCode());
    }

    // ********************************************** JOIN GAME TESTS ***************************************************

    @Test
    void testJoinGame_whenGameExists_thenGameStateReturned() {
        RockPaperScissorsGame mockGame = mock(RockPaperScissorsGame.class);
        RockPaperScissorsGameState mockState = mock(RockPaperScissorsGameState.class);
        Player mockPlayer = mock(Player.class);
        when(gameRepository.getGame(anyString())).thenReturn(mockGame);
        when(mockGame.getState()).thenReturn(mockState);

        gameService.joinGame(gameId, mockPlayer);

        verify(gameRepository, times(1)).getGame(gameId);
        verify(mockGame, times(1)).joinGame(mockPlayer);
        verify(mockGame, times(1)).getState();

        RockPaperScissorsGameState resultState = gameService.getGameState(gameId);
        assertEquals(mockState, resultState);
    }

    @Test
    void testJoinGame_whenGameDoesNotExist_thenThrowsGameNotFoundException() {
        when(gameRepository.getGame(anyString())).thenReturn(null);

        GameNotFoundException exception = assertThrows(GameNotFoundException.class, () -> {
            gameService.joinGame(gameId, playerOne);
        });

        assertEquals(gameNotFoundMessage + gameId, exception.getMessage());
        assertEquals(gameNotFoundError, exception.getErrorCode());
    }

    @Test
    void testJoinGame_whenPlayerIsNull_thenThrowsPlayerNullException() {
        PlayerNullException exception = assertThrows(PlayerNullException.class, () -> {
            gameService.joinGame(gameId, null);
        });

        assertEquals(playerNullMessage, exception.getMessage());
    }


    // ********************************************** MAKE MOVE TESTS ***************************************************

    @Test
    void testMakeMove_whenGameExists_thenGameStateReturned() {
        RockPaperScissorsGame mockGame = mock(RockPaperScissorsGame.class);
        RockPaperScissorsGameState mockState = mock(RockPaperScissorsGameState.class);
        PlayerMove mockMove = mock(PlayerMove.class);
        when(gameRepository.getGame(anyString())).thenReturn(mockGame);
        when(mockGame.getState()).thenReturn(mockState);

        gameService.makeMove(gameId, mockMove);

        verify(gameRepository, times(1)).getGame(gameId);
        verify(mockGame, times(1)).makeMove(mockMove);
        verify(mockGame, times(1)).getState();

        RockPaperScissorsGameState resultState = gameService.getGameState(gameId);
        assertEquals(mockState, resultState);
    }

    @Test
    void testMakeMove_whenGameDoesNotExist_thenThrowsGameNotFoundException() {
        when(gameRepository.getGame(anyString())).thenReturn(null);

        GameNotFoundException exception = assertThrows(GameNotFoundException.class, () -> {
            gameService.makeMove(gameId, playerMove);
        });

        assertEquals(gameNotFoundMessage + gameId, exception.getMessage());
        assertEquals(gameNotFoundError, exception.getErrorCode());
    }

    @Test
    void testMakeMove_whenPlayerMoveIsNull_thenThrowsPlayerMoveNullException() {
        PlayerMoveNullException exception = assertThrows(PlayerMoveNullException.class, () -> {
            gameService.makeMove(gameId, null);
        });

        assertEquals(playerMoveNullMessage, exception.getMessage());
        assertEquals(playerMoveNullError, exception.getErrorCode());
    }
}