package com.magneticmojo.rpsapi.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magneticmojo.rpsapi.api.exceptions.gameexception.GameFullException;
import com.magneticmojo.rpsapi.api.exceptions.playerexception.MultipleMovesProhibitedException;
import com.magneticmojo.rpsapi.api.exceptions.playerexception.PlayerNotInGameException;
import com.magneticmojo.rpsapi.api.model.entities.Move;
import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import com.magneticmojo.rpsapi.api.model.responses.GameCreatedResponse;
import com.magneticmojo.rpsapi.api.state.*;
import com.magneticmojo.rpsapi.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
@AutoConfigureMockMvc
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GameService gameService;

    private Player playerOne;
    private PlayerMove firstPlayerMove;

    @BeforeEach
    public void setUp() {
        playerOne = new Player("playerOne");
        firstPlayerMove = new PlayerMove(playerOne, Move.ROCK);
    }

    @Test
    public void testCreateGame() throws Exception {
        GameCreatedResponse gameCreatedResponse = new GameCreatedResponse("Rock-Papper-Scissors game created", "gameId");
        Mockito.when(gameService.createGame(playerOne)).thenReturn(gameCreatedResponse.id());

        mockMvc.perform(post("/api/games")
                        .content(objectMapper.writeValueAsString(playerOne))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(gameCreatedResponse.message()))
                .andExpect(jsonPath("$.id").value(gameCreatedResponse.id()));
    }

    @Test
    public void testGetGameState_PlayerOneJoined() throws Exception {
        String gameId = "gameId";
        GameState gameState = new PlayerOneJoinedState(playerOne);
        Mockito.when(gameService.getGameState(gameId)).thenReturn(gameState);

        mockMvc.perform(get("/api/games/" + gameId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerOne").value(playerOne.name()));
    }

    @Test
    public void testGetGameState_PlayerTwoJoined() throws Exception {
        String gameId = "gameId";
        Player playerTwo = new Player("playerTwo");
        GameState gameState = new PlayerTwoJoinedState(playerOne, playerTwo);
        Mockito.when(gameService.getGameState(gameId)).thenReturn(gameState);

        mockMvc.perform(get("/api/games/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerOne").value(playerOne.name()))
                .andExpect(jsonPath("$.playerTwo").value(playerTwo.name()));
    }

    @Test
    public void testGetGameState_FirstMoveMadeAwaitingLastMove() throws Exception {
        String gameId = "gameId";
        Player playerTwo = new Player("playerTwo");
        GameState gameState = new FirstMoveMadeAwaitingLastMoveState(playerOne, playerTwo, firstPlayerMove);
        Mockito.when(gameService.getGameState(gameId)).thenReturn(gameState);

        mockMvc.perform(get("/api/games/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerOne").value(playerOne.name()))
                .andExpect(jsonPath("$.playerTwo").value(playerTwo.name()))
                .andExpect(jsonPath("$.firstMoveBy").value(playerOne.name()));
    }

    @Test
    public void testGetGameState_GameEndedState() throws Exception {
        String gameId = "gameId";
        Player playerTwo = new Player("playerTwo");
        PlayerMove lastPlayerMove = new PlayerMove(playerTwo, Move.SCISSORS);
        String gameResult = playerOne.name() + " won by "
                + firstPlayerMove.move().name() + " beating "
                + lastPlayerMove.move().name() + ". "
                + playerTwo.name() + " lost";
        GameState gameState = new GameEndedState(playerOne, playerTwo, firstPlayerMove, lastPlayerMove, gameResult);
        Mockito.when(gameService.getGameState(gameId)).thenReturn(gameState);

        mockMvc.perform(get("/api/games/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerOne").value(playerOne.name()))
                .andExpect(jsonPath("$.playerTwo").value(playerTwo.name()))
                .andExpect(jsonPath("$.firstMoveBy").value(playerOne.name() + " (" + firstPlayerMove.move().name() + ")"))
                .andExpect(jsonPath("$.lastMoveBy").value(playerTwo.name() + " (" + lastPlayerMove.move().name() + ")"))
                .andExpect(jsonPath("$.gameResult").value(gameResult));
    }


    @Test
    public void testJoinGame() throws Exception {
        String gameId = "gameId";
        GameState gameState = new PlayerTwoJoinedState(playerOne, new Player("playerTwo"));
        Mockito.when(gameService.joinGame(gameId, playerOne)).thenReturn(gameState);

        mockMvc.perform(patch("/api/games/" + gameId + "/join")
                        .content(objectMapper.writeValueAsString(playerOne))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testMakeMove() throws Exception {
        String gameId = "gameId";
        GameState gameState = new FirstMoveMadeAwaitingLastMoveState(playerOne, new Player("playerTwo"), firstPlayerMove);
        Mockito.when(gameService.makeMove(gameId, firstPlayerMove)).thenReturn(gameState);

        mockMvc.perform(patch("/api/games/" + gameId + "/move")
                        .content(objectMapper.writeValueAsString(firstPlayerMove))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testJoinGame_GameExceptionThrown() throws Exception {
        String gameId = "gameId";
        Mockito.when(gameService.joinGame(gameId, playerOne)).thenThrow(new GameFullException("Game full. Cannot join game"));

        mockMvc.perform(patch("/api/games/" + gameId + "/join")
                        .content(objectMapper.writeValueAsString(playerOne))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage").value("Game full. Cannot join game"));
    }

    @Test
    public void testMakeMove_GameExceptionThrown() throws Exception {
        String gameId = "gameId";
        Mockito.when(gameService.makeMove(gameId, firstPlayerMove)).thenThrow(new MultipleMovesProhibitedException("Player already made move. Cannot make another move"));

        mockMvc.perform(patch("/api/games/" + gameId + "/move")
                        .content(objectMapper.writeValueAsString(firstPlayerMove))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage").value("Player already made move. Cannot make another move"));
    }

    @Test
    public void testJoinGame_PlayerExceptionThrown() throws Exception {
        String gameId = "gameId";
        Mockito.when(gameService.joinGame(gameId, playerOne)).thenThrow(new PlayerNotInGameException("Player not in game. Cannot make move"));

        mockMvc.perform(patch("/api/games/" + gameId + "/join")
                        .content(objectMapper.writeValueAsString(playerOne))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Player not in game. Cannot make move"));
    }

    @Test
    public void testMakeMove_PlayerExceptionThrown() throws Exception {
        String gameId = "gameId";
        Mockito.when(gameService.makeMove(gameId, firstPlayerMove)).thenThrow(new PlayerNotInGameException("Player is not in the game."));

        mockMvc.perform(patch("/api/games/" + gameId + "/move")
                        .content(objectMapper.writeValueAsString(firstPlayerMove))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Player is not in the game."));
    }


}
