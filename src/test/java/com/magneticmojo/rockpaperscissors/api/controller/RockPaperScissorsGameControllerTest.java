package com.magneticmojo.rockpaperscissors.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.exceptions.GameNotFoundException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.*;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Move;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;
import com.magneticmojo.rockpaperscissors.api.model.responses.gameresponses.CreateGameResponse;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.GameEndedState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.RockPaperScissorsGameState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.PlayerOneJoinedState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.PlayerTwoJoinedState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.*;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.RockPaperScissorsGameService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This is a test class for the RockPaperScissorsGameController class.
 * It aims to verify the behavior of the RockPaperScissorsGameController class by using mock implementations
 * of the dependent classes and the MockMvc testing framework.
 * It covers all public API endpoints exposed by RockPaperScissorsGameController such as creating a new game, joining an existing game,
 * making a move in the game, and retrieving the current game state.
 */
@WebMvcTest(RockPaperScissorsGameController.class)
public class RockPaperScissorsGameControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private RockPaperScissorsGameService gameService;

    private final Player playerOne = new Player("playerOne");
    private final Player playerTwo = new Player("playerTwo");
    private final Player playerThree = new Player("playerThree");

    private final Player playerWithNullName = new Player(null);
    private final Player playerWithBlankName = new Player("");
    private final Player playerWithWhitespaceName = new Player(" ");

    private final PlayerMove firstPlayerMove = new PlayerMove(playerOne, Move.ROCK);
    private final PlayerMove lastPlayerMove = new PlayerMove(playerTwo, Move.SCISSORS);

    private final String gameId = "bf4422fe-f78a-4625-acdb-711db7838f99";
    private final String invalidGameId = "invalidGameId";
    private String gameResult = playerOne.name() + " won by "
            + firstPlayerMove.move().name() + " beating "
            + lastPlayerMove.move().name() + ". "
            + playerTwo.name() + " lost";

    private final String gameNotFoundMessage = "Invalid id: ";
    private final String gameNotFoundErrorCode = "GAME_NOT_FOUND";
    private final String gameFullMessage = "Game full. Cannot join game";
    private final String gameFullErrorCode = "GAME_FULL";
    private final String gameEndedMessage = "Game ended. Cannot join game";
    private final String gameEndedErrorCode = "GAME_ENDED";
    private final String playerNotInGameMessage = "Player not in game. Cannot make move";
    private final String playerNotInGameErrorCode = "PLAYER_NOT_IN_GAME";
    private final String multipleMovesProhibitedMessage = "Player already made move. Cannot make another move";
    private final String multipleMovesProhibitedErrorCode = "MULTIPLE_MOVES_PROHIBITED";
    private final String missingPlayerTwoMessage = "Move prohibited. Player two not joined";
    private final String missingPlayerTwoErrorCode = "MISSING_PLAYER_TWO";

    private final CreateGameResponse createGameResponse = new CreateGameResponse(gameId, playerOne);
    private final PlayerOneJoinedState playerOneJoinedState = new PlayerOneJoinedState(playerOne);
    private final PlayerTwoJoinedState playerTwoJoinedState = new PlayerTwoJoinedState(playerOne, playerTwo);
    private final FirstMoveMadeState firstMoveMadeState = new FirstMoveMadeState(playerOne, playerTwo, firstPlayerMove);
    private final GameEndedState gameEndedState = new GameEndedState(playerOne, playerTwo, firstPlayerMove, lastPlayerMove);

    RockPaperScissorsGameControllerTest(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    // ********************************************** CREATE GAME TESTS *************************************************

    @Test
    public void testCreateGame_withValidPlayer_ReturnsCreatedStatusAndValidGameId() throws Exception {
        Mockito.when(gameService.createGame(playerOne)).thenReturn(createGameResponse.id());

        mockMvc.perform(post("/api/games")
                        .content(objectMapper.writeValueAsString(playerOne))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.gameId").value(createGameResponse.id()));

        Mockito.verify(gameService, Mockito.times(1)).createGame(playerOne);
    }

    @Test
    public void testCreateGame_withInvalidPlayerName_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/games")
                        .content(objectMapper.writeValueAsString(playerWithNullName))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verify(gameService, Mockito.times(0)).createGame(playerWithNullName);

        mockMvc.perform(post("/api/games")
                        .content(objectMapper.writeValueAsString(playerWithBlankName))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verify(gameService, Mockito.times(0)).createGame(playerWithBlankName);

        mockMvc.perform(post("/api/games")
                        .content(objectMapper.writeValueAsString(playerWithWhitespaceName))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verify(gameService, Mockito.times(0)).createGame(playerWithWhitespaceName);
    }

    @Test
    public void testCreateGame_yieldsCorrectState() throws Exception {
        Mockito.when(gameService.createGame(playerOne)).thenReturn(createGameResponse.id());

        mockMvc.perform(post("/api/games")
                        .content(objectMapper.writeValueAsString(playerOne))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.gameId").value(createGameResponse.id()));

        Mockito.verify(gameService, Mockito.times(1)).createGame(playerOne);

        Mockito.when(gameService.getGameState(createGameResponse.id())).thenReturn(playerOneJoinedState);
        RockPaperScissorsGameState gameState = gameService.getGameState(createGameResponse.id());
        assertTrue(gameState instanceof PlayerOneJoinedState);

        Mockito.verify(gameService, Mockito.times(1)).getGameState(gameId);
    }


    // ********************************************** GET GAME STATE TESTS *************************************************

    @Test
    public void testGetGameState_PlayerOneJoined() throws Exception {
        Mockito.when(gameService.getGameState(gameId)).thenReturn(playerOneJoinedState);

        mockMvc.perform(get("/api/games/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerOne").value(playerOne.name()));

        Mockito.verify(gameService, Mockito.times(1)).getGameState(gameId);
    }

    @Test
    public void testGetGameState_PlayerTwoJoined() throws Exception {
        Mockito.when(gameService.getGameState(gameId)).thenReturn(playerTwoJoinedState);

        mockMvc.perform(get("/api/games/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerOne").value(playerOne.name()))
                .andExpect(jsonPath("$.playerTwo").value(playerTwo.name()));

        Mockito.verify(gameService, Mockito.times(1)).getGameState(gameId);
    }

    @Test
    public void testGetGameState_FirstMoveMade() throws Exception {
        Mockito.when(gameService.getGameState(gameId)).thenReturn(firstMoveMadeState);

        mockMvc.perform(get("/api/games/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerOne").value(playerOne.name()))
                .andExpect(jsonPath("$.playerTwo").value(playerTwo.name()))
                .andExpect(jsonPath("$.firstMoveBy").value(playerOne.name()));

        Mockito.verify(gameService, Mockito.times(1)).getGameState(gameId);
    }

    @Test
    public void testGetGameState_GameEndedState() throws Exception {
        String firstMoveByValue = playerOne.name() + " (" + firstPlayerMove.move().name() + ")";
        String lastMoveByValue = playerTwo.name() + " (" + lastPlayerMove.move().name() + ")";

        Mockito.when(gameService.getGameState(gameId)).thenReturn(gameEndedState);

        mockMvc.perform(get("/api/games/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerOne").value(playerOne.name()))
                .andExpect(jsonPath("$.playerTwo").value(playerTwo.name()))
                .andExpect(jsonPath("$.firstMoveBy").value(firstMoveByValue))
                .andExpect(jsonPath("$.lastMoveBy").value(lastMoveByValue))
                .andExpect(jsonPath("$.gameResult").value(gameResult));

        Mockito.verify(gameService, Mockito.times(1)).getGameState(gameId);
    }

    @Test
    public void testGetGameState_InvalidGameId_YieldsNotFoundStatus() throws Exception {
        Mockito.when(gameService.getGameState(invalidGameId)).thenThrow(new GameNotFoundException(gameNotFoundMessage + invalidGameId));

        mockMvc.perform(get("/api/games/" + invalidGameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof GameNotFoundException))
                .andExpect(result -> {
                    Exception resolvedException = result.getResolvedException();
                    if (resolvedException != null) {
                        assertEquals(gameNotFoundMessage + invalidGameId, resolvedException.getMessage());
                        if (resolvedException instanceof GameNotFoundException) {
                            assertEquals(gameNotFoundErrorCode, ((GameNotFoundException) resolvedException).getErrorCode());
                        }
                    }
                });

        Mockito.verify(gameService, Mockito.times(1)).getGameState(invalidGameId);
    }

    // ********************************************** JOIN GAME TESTS *************************************************

    @Test
    public void testJoinGame_withPlayerTwo_returnStatusOK() throws Exception {
        Mockito.when(gameService.joinGame(gameId, playerTwo)).thenReturn(playerTwoJoinedState);

        mockMvc.perform(patch("/api/games/" + gameId + "/join")
                        .content(objectMapper.writeValueAsString(playerTwo))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(gameService, Mockito.times(1)).joinGame(gameId, playerTwo);
    }

    @Test
    public void testJoinGame_withInvalidPlayerName_ReturnsBadRequest() throws Exception {
        mockMvc.perform(patch("/api/games/" + gameId + "/join")
                        .content(objectMapper.writeValueAsString(playerWithNullName))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verify(gameService, Mockito.times(0)).joinGame(gameId, playerWithNullName);

        mockMvc.perform(patch("/api/games/" + gameId + "/join")
                        .content(objectMapper.writeValueAsString(playerWithBlankName))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verify(gameService, Mockito.times(0)).joinGame(gameId, playerWithBlankName);

        mockMvc.perform(patch("/api/games/" + gameId + "/join")
                        .content(objectMapper.writeValueAsString(playerWithWhitespaceName))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verify(gameService, Mockito.times(0)).joinGame(gameId, playerWithWhitespaceName);
    }

    @Test
    public void testJoinGame_withInvalidGameId_throwsGameNotFoundException() throws Exception {
        Mockito.when(gameService.joinGame(invalidGameId, playerTwo)).thenThrow(new GameNotFoundException(gameNotFoundMessage + invalidGameId));

        mockMvc.perform(patch("/api/games/" + invalidGameId + "/join")
                        .content(objectMapper.writeValueAsString(playerTwo))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value(gameNotFoundMessage + invalidGameId))
                .andExpect(jsonPath("$.errorCode").value(gameNotFoundErrorCode));

        Mockito.verify(gameService, Mockito.times(1)).joinGame(invalidGameId, playerTwo);
    }

    @Test
    public void testJoinGame_whenGameFull_throwsGameFullException() throws Exception {
        Mockito.when(gameService.joinGame(gameId, playerThree)).thenThrow(new GameFullException(gameFullMessage));

        mockMvc.perform(patch("/api/games/" + gameId + "/join")
                        .content(objectMapper.writeValueAsString(playerThree))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage").value(gameFullMessage))
                .andExpect(jsonPath("$.errorCode").value(gameFullErrorCode));

        Mockito.verify(gameService, Mockito.times(1)).joinGame(gameId, playerThree);
    }

    @Test
    public void testJoinGame_whenGameEnded_throwsGameEndedException() throws Exception {
        Mockito.when(gameService.joinGame(gameId, playerTwo)).thenThrow(new GameEndedException(gameEndedMessage));

        mockMvc.perform(patch("/api/games/" + gameId + "/join")
                        .content(objectMapper.writeValueAsString(playerTwo))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage").value(gameEndedMessage))
                .andExpect(jsonPath("$.errorCode").value(gameEndedErrorCode));

        Mockito.verify(gameService, Mockito.times(1)).joinGame(gameId, playerTwo);
    }

    // ********************************************** MAKE MOVE TESTS *************************************************

    @Test
    public void testMakeMove_returnStatusOK() throws Exception {
        Mockito.when(gameService.makeMove(gameId, firstPlayerMove)).thenReturn(firstMoveMadeState);

        mockMvc.perform(patch("/api/games/" + gameId + "/move")
                        .content(objectMapper.writeValueAsString(firstPlayerMove))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(gameService, Mockito.times(1)).makeMove(gameId, firstPlayerMove);
    }

    @Test
    public void testMakeMove_InvalidGameId_ThrowsGameNotFoundException() throws Exception {
        Mockito.when(gameService.makeMove(Mockito.anyString(), Mockito.any(PlayerMove.class))).thenThrow(new GameNotFoundException(gameNotFoundMessage + invalidGameId));

        mockMvc.perform(patch("/api/games/" + invalidGameId + "/move")
                        .content(objectMapper.writeValueAsString(firstPlayerMove))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value(gameNotFoundMessage + invalidGameId))
                .andExpect(jsonPath("$.errorCode").value(gameNotFoundErrorCode));

        Mockito.verify(gameService, Mockito.times(1)).makeMove(Mockito.anyString(), Mockito.any(PlayerMove.class));
    }


    @Test
    public void testMakeMove_invalidRequestInput_badRequestResponse() throws Exception {
        PlayerMove playerMove = new PlayerMove(new Player(null), Move.ROCK);

        mockMvc.perform(patch("/api/games/" + gameId + "/move")
                        .content(objectMapper.writeValueAsString(playerMove))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verify(gameService, Mockito.times(0)).makeMove(Mockito.anyString(), Mockito.any(PlayerMove.class));


        mockMvc.perform(patch("/api/games/" + gameId + "/move")
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verify(gameService, Mockito.times(0)).makeMove(Mockito.anyString(), Mockito.any(PlayerMove.class));

        PlayerMove playerMove2 = new PlayerMove(playerOne, null);

        mockMvc.perform(patch("/api/games/" + gameId + "/move")
                        .content(objectMapper.writeValueAsString(playerMove2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verify(gameService, Mockito.times(0)).makeMove(Mockito.anyString(), Mockito.any(PlayerMove.class));
    }

    @Test
    public void testMakeMove_missingPlayerTwoExceptionThrown() throws Exception {
        Mockito.when(gameService.makeMove(gameId, firstPlayerMove)).thenThrow(new MissingPlayerTwoException(missingPlayerTwoMessage));

        mockMvc.perform(patch("/api/games/" + gameId + "/move")
                        .content(objectMapper.writeValueAsString(firstPlayerMove))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage").value(missingPlayerTwoMessage))
                .andExpect(jsonPath("$.errorCode").value(missingPlayerTwoErrorCode));

        Mockito.verify(gameService, Mockito.times(1)).makeMove(gameId, firstPlayerMove);
    }

    @Test
    public void testMakeMove_playerNotInGameExceptionThrown() throws Exception {
        Mockito.when(gameService.makeMove(gameId, firstPlayerMove)).thenThrow(new PlayerNotInGameException(playerNotInGameMessage));

        mockMvc.perform(patch("/api/games/" + gameId + "/move")
                        .content(objectMapper.writeValueAsString(firstPlayerMove))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value(playerNotInGameMessage))
                .andExpect(jsonPath("$.errorCode").value(playerNotInGameErrorCode));

        Mockito.verify(gameService, Mockito.times(1)).makeMove(gameId, firstPlayerMove);
    }

    @Test
    public void testMakeMove_multipleMovesProhibitedExceptionThrown() throws Exception {
        Mockito.when(gameService.makeMove(gameId, firstPlayerMove)).thenThrow(new MultipleMovesProhibitedException(multipleMovesProhibitedMessage));

        mockMvc.perform(patch("/api/games/" + gameId + "/move")
                        .content(objectMapper.writeValueAsString(firstPlayerMove))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage").value(multipleMovesProhibitedMessage))
                .andExpect(jsonPath("$.errorCode").value(multipleMovesProhibitedErrorCode));

        Mockito.verify(gameService, Mockito.times(1)).makeMove(gameId, firstPlayerMove);
    }

    @Test
    public void testMakeMove_gameEndedExceptionThrown() throws Exception {
        Mockito.when(gameService.makeMove(gameId, firstPlayerMove)).thenThrow(new GameEndedException(gameEndedMessage));

        mockMvc.perform(patch("/api/games/" + gameId + "/move")
                        .content(objectMapper.writeValueAsString(firstPlayerMove))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage").value(gameEndedMessage))
                .andExpect(jsonPath("$.errorCode").value(gameEndedErrorCode));

        Mockito.verify(gameService, Mockito.times(1)).makeMove(gameId, firstPlayerMove);
    }
}
