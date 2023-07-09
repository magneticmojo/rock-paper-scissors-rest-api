package com.magneticmojo.rockpaperscissors.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.GameFullException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.MultipleMovesProhibitedException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.PlayerNotInGameException;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This is a test class for the GameController class.
 * It aims to verify the behavior of the GameController class by using mock implementations
 * of the dependent classes and the MockMvc testing framework.
 * It covers all public API endpoints exposed by GameController such as creating a new game, joining an existing game,
 * making a move in the game, and retrieving the current game state.
 * It tests various possible states of the game, ensuring the correctness of the controller
 * in different situations such as a game is full, a player making multiple moves, and a player who is not in the game.
 */

// TODO MIXING OF PLAYERONE AND PLAYERTWO
@WebMvcTest(GameController.class)
public class GameControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private RockPaperScissorsGameService rockPaperScissorsGameService;

    private Player playerOne;
    private PlayerMove firstPlayerMove;
    private Player playerTwo;
    private PlayerMove lastPlayerMove;
    private String gameId;

    GameControllerTest(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    public void setUp() {
        playerOne = new Player("playerOne");
        firstPlayerMove = new PlayerMove(playerOne, Move.ROCK);
        playerTwo = new Player("playerTwo");
        lastPlayerMove = new PlayerMove(playerTwo, Move.PAPER);
    }

    @Test
    public void testCreateGame() throws Exception {
        CreateGameResponse createGameResponse = new CreateGameResponse("gameId", playerOne);
        Mockito.when(rockPaperScissorsGameService.createGame(playerOne)).thenReturn(createGameResponse.id());

        mockMvc.perform(post("/api/games")
                        .content(objectMapper.writeValueAsString(playerOne))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.gameId").value(createGameResponse.id()));
    }

    @Test
    public void testGetGameState_PlayerOneJoined() throws Exception {
        String gameId = "gameId";
        RockPaperScissorsGameState rockPaperScissorsGameState = new PlayerOneJoinedState(playerOne);
        Mockito.when(rockPaperScissorsGameService.getGameState(gameId)).thenReturn(rockPaperScissorsGameState);

        mockMvc.perform(get("/api/games/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerOne").value(playerOne.name()));

        Mockito.verify(rockPaperScissorsGameService, Mockito.times(1)).getGameState(gameId);
    }

    @Test
    public void testGetGameState_PlayerTwoJoined() throws Exception {
        String gameId = "gameId";
        RockPaperScissorsGameState rockPaperScissorsGameState = new PlayerTwoJoinedState(playerOne, playerTwo);
        Mockito.when(rockPaperScissorsGameService.getGameState(gameId)).thenReturn(rockPaperScissorsGameState);

        mockMvc.perform(get("/api/games/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerOne").value(playerOne.name()))
                .andExpect(jsonPath("$.playerTwo").value(playerTwo.name()));
    }

    @Test
    public void testGetGameState_FirstMoveMadeAwaitingLastMove() throws Exception {
        String gameId = "gameId";
        RockPaperScissorsGameState rockPaperScissorsGameState = new FirstMoveMadeState(playerOne, playerTwo, firstPlayerMove);
        Mockito.when(rockPaperScissorsGameService.getGameState(gameId)).thenReturn(rockPaperScissorsGameState);

        mockMvc.perform(get("/api/games/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerOne").value(playerOne.name()))
                .andExpect(jsonPath("$.playerTwo").value(playerTwo.name()))
                .andExpect(jsonPath("$.firstMoveBy").value(playerOne.name()));

        Mockito.verify(rockPaperScissorsGameService, Mockito.times(1)).getGameState(gameId);
    }

    @Test
    public void testGetGameState_GameEndedState() throws Exception {
        String gameId = "gameId";
        PlayerMove lastPlayerMove = new PlayerMove(playerTwo, Move.SCISSORS);
        String gameResult = playerOne.name() + " won by "
                + firstPlayerMove.move().name() + " beating "
                + lastPlayerMove.move().name() + ". "
                + playerTwo.name() + " lost";
        RockPaperScissorsGameState rockPaperScissorsGameState = new GameEndedState(playerOne, playerTwo, firstPlayerMove, lastPlayerMove);
        Mockito.when(rockPaperScissorsGameService.getGameState(gameId)).thenReturn(rockPaperScissorsGameState);

        mockMvc.perform(get("/api/games/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerOne").value(playerOne.name()))
                .andExpect(jsonPath("$.playerTwo").value(playerTwo.name()))
                .andExpect(jsonPath("$.firstMoveBy").value(playerOne.name() + " (" + firstPlayerMove.move().name() + ")"))
                .andExpect(jsonPath("$.lastMoveBy").value(playerTwo.name() + " (" + lastPlayerMove.move().name() + ")"))
                .andExpect(jsonPath("$.gameResult").value(gameResult));

        Mockito.verify(rockPaperScissorsGameService, Mockito.times(1)).getGameState(gameId);
    }


    @Test
    public void testJoinGame_returnStatusOK() throws Exception {
        String gameId = "gameId";
        RockPaperScissorsGameState rockPaperScissorsGameState = new PlayerTwoJoinedState(playerOne, playerTwo);
        Mockito.when(rockPaperScissorsGameService.joinGame(gameId, playerTwo)).thenReturn(rockPaperScissorsGameState);

        mockMvc.perform(patch("/api/games/" + gameId + "/join")
                        .content(objectMapper.writeValueAsString(playerTwo))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(rockPaperScissorsGameService, Mockito.times(1)).joinGame(gameId, playerTwo);
    }

    @Test
    public void testMakeMove_returnStatusOK() throws Exception {
        String gameId = "gameId";
        RockPaperScissorsGameState rockPaperScissorsGameState = new FirstMoveMadeState(playerOne, playerTwo, firstPlayerMove);
        Mockito.when(rockPaperScissorsGameService.makeMove(gameId, firstPlayerMove)).thenReturn(rockPaperScissorsGameState);

        mockMvc.perform(patch("/api/games/" + gameId + "/move")
                        .content(objectMapper.writeValueAsString(firstPlayerMove))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(rockPaperScissorsGameService, Mockito.times(1)).makeMove(gameId, firstPlayerMove);
    }

    @Test
    public void testJoinGame_GameExceptionThrown() throws Exception {
        String gameId = "gameId";
        Mockito.when(rockPaperScissorsGameService.joinGame(gameId, playerOne)).thenThrow(new GameFullException("Game full. Cannot join game"));

        mockMvc.perform(patch("/api/games/" + gameId + "/join")
                        .content(objectMapper.writeValueAsString(playerOne))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage").value("Game full. Cannot join game"));
    }

    @Test
    public void testMakeMove_GameExceptionThrown() throws Exception {
        String gameId = "gameId";
        Mockito.when(rockPaperScissorsGameService.makeMove(gameId, firstPlayerMove)).thenThrow(new MultipleMovesProhibitedException("Player already made move. Cannot make another move"));

        mockMvc.perform(patch("/api/games/" + gameId + "/move")
                        .content(objectMapper.writeValueAsString(firstPlayerMove))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage").value("Player already made move. Cannot make another move"));

        Mockito.verify(rockPaperScissorsGameService, Mockito.times(1)).makeMove(gameId, firstPlayerMove);
    }

    @Test
    public void testJoinGame_PlayerExceptionThrown() throws Exception {
        String gameId = "gameId";
        Mockito.when(rockPaperScissorsGameService.joinGame(gameId, playerOne)).thenThrow(new PlayerNotInGameException("Player not in game. Cannot make move"));

        mockMvc.perform(patch("/api/games/" + gameId + "/join")
                        .content(objectMapper.writeValueAsString(playerOne))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Player not in game. Cannot make move"));
    }

    @Test
    public void testMakeMove_PlayerExceptionThrown() throws Exception {
        String gameId = "gameId";
        Mockito.when(rockPaperScissorsGameService.makeMove(gameId, firstPlayerMove)).thenThrow(new PlayerNotInGameException("Player is not in the game."));

        mockMvc.perform(patch("/api/games/" + gameId + "/move")
                        .content(objectMapper.writeValueAsString(firstPlayerMove))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Player is not in the game."));

        Mockito.verify(rockPaperScissorsGameService, Mockito.times(1)).makeMove(gameId, firstPlayerMove);
    }
}
