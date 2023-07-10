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

    private static final String TEST_GAME_ID = "d75bceed-c780-4f66-9a34-8da792db0976";
    private Player playerOne = new Player("playerOne");

    @BeforeEach
    void setUp() {
        gameRepository = Mockito.mock(InMemoryRockPaperScissorsGameRepository.class);
        gameService = new RockPaperScissorsGameService(gameRepository);
    }

    // ********************************************** CREATE GAME TESTS *************************************************

    // TODO -> Add test for when game already exists
    // TODO -> Add test for when game repository throws exception


    @Test
    void testCreateGame_returnsNonNullId_andCallsAddGame() {
        doNothing().when(gameRepository).addGame(any(RockPaperScissorsGame.class));

        String id = gameService.createGame(playerOne);
        assertNotNull(id);

        verify(gameRepository, times(1)).addGame(any(RockPaperScissorsGame.class));
    }



    // ********************************************** GET GAME STATE TESTS **********************************************

    @Test
    void testGetGameState_whenGameExists_thenGameStateReturned() {
        RockPaperScissorsGame mockGame = mock(RockPaperScissorsGame.class);
        RockPaperScissorsGameState mockState = mock(RockPaperScissorsGameState.class);
        when(gameRepository.getGame(anyString())).thenReturn(mockGame);
        when(mockGame.getState()).thenReturn(mockState);

        RockPaperScissorsGameState resultState = gameService.getGameState(TEST_GAME_ID);

        assertEquals(mockState, resultState);
        verify(gameRepository, times(1)).getGame(TEST_GAME_ID);
        verify(mockGame, times(1)).getState();
    }


    @Test
    void testGetGameState_whenGameDoesNotExist_thenThrowsGameNotFoundException() {
        when(gameRepository.getGame(anyString())).thenReturn(null);

        Exception exception = assertThrows(GameNotFoundException.class, () -> {
            gameService.getGameState(TEST_GAME_ID);
        });

        assertEquals("Invalid id: " + TEST_GAME_ID, exception.getMessage());
    }

    // ********************************************** JOIN GAME TESTS ***************************************************

    @Test
    void testJoinGame_whenGameExists_thenGameStateReturned() {
        RockPaperScissorsGame mockGame = mock(RockPaperScissorsGame.class);
        RockPaperScissorsGameState mockState = mock(RockPaperScissorsGameState.class);
        Player mockPlayer = mock(Player.class);
        when(gameRepository.getGame(anyString())).thenReturn(mockGame);
        when(mockGame.getState()).thenReturn(mockState);

        gameService.joinGame(TEST_GAME_ID, mockPlayer);

        verify(gameRepository, times(1)).getGame(TEST_GAME_ID);
        verify(mockGame, times(1)).joinGame(mockPlayer);
        verify(mockGame, times(1)).getState();

        RockPaperScissorsGameState resultState = gameService.getGameState(TEST_GAME_ID);
        assertEquals(mockState, resultState);
    }

    @Test
    void testJoinGame_whenGameDoesNotExist_thenThrowsGameNotFoundException() {
        when(gameRepository.getGame(anyString())).thenReturn(null);

        Exception exception = assertThrows(GameNotFoundException.class, () -> {
            gameService.joinGame(TEST_GAME_ID, new Player("somePlayer"));
        });

        assertEquals("Invalid id: " + TEST_GAME_ID, exception.getMessage());
    }


    // ********************************************** MAKE MOVE TESTS ***************************************************

    @Test
    void testMakeMove_whenGameExists_thenGameStateReturned() {
        RockPaperScissorsGame mockGame = mock(RockPaperScissorsGame.class);
        RockPaperScissorsGameState mockState = mock(RockPaperScissorsGameState.class);
        PlayerMove mockMove = mock(PlayerMove.class);
        when(gameRepository.getGame(anyString())).thenReturn(mockGame);
        when(mockGame.getState()).thenReturn(mockState);

        gameService.makeMove(TEST_GAME_ID, mockMove);

        verify(gameRepository, times(1)).getGame(TEST_GAME_ID);
        verify(mockGame, times(1)).makeMove(mockMove);
        verify(mockGame, times(1)).getState();

        RockPaperScissorsGameState resultState = gameService.getGameState(TEST_GAME_ID);
        assertEquals(mockState, resultState);
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