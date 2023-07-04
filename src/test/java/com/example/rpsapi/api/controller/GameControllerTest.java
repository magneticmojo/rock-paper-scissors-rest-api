/*
package com.example.rpsapi.api.controller;

import com.example.rpsapi.api.model.entities.Player;
import com.example.rpsapi.api.repository.GameRepository;
import com.example.rpsapi.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@SpringBootTest // TEST entire application context
//@AutoConfigureMockMvc // Full Spring Application context Without Server
*/
/*Another useful approach is to not start the server at all but to test only the layer below that,
where Spring handles the incoming HTTP request and hands it off to your controller. *//*


@WebMvcTest(GameController.class) // TEST only web layer
public class GameControllerTest {

    @MockBean
    private GameService gameService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;


    @Test // POST --> /api/games
    public void shouldCreateGameWithPlayerOneAndReturnGameID() throws Exception {

        GameService gameService = new GameService(new GameRepository());
        Player playerOne = new Player("PlayerOne");

        when(gameService.createGame(playerOne)).thenReturn(anyString());

        mockMvc.perform(post("/api/games").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameID", notNullValue()))
                .andDO(print());


    }

    @Test // GET --> /api/games/{gameID}
    public void shouldReturnGameState() {
        GameService gameService = new GameService(new GameRepository());

        String gameID = gameService.createGame(new Player("PlayerOne"));

        when(gameService.getGameState(gameID)).thenReturn(anyString());



    }

    @Test // PATCH --> /api/games/{gameID}/join
    public void shouldJoinPLayerTwoInGameWithSpecifiedGameID() {
    }

    @Test // PATCH --> /api/games/{gameID}/move
    public void shouldMakeConsumingPlayerMakeAMoveInGameWithSpecifiedGameID() {
    }


}*/
