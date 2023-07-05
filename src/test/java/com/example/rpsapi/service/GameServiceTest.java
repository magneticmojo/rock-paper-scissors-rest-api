/*
package com.example.rpsapi.service;

import com.example.rpsapi.api.model.dto.MoveRequest;
import com.example.rpsapi.api.model.entities.Move;
import com.example.rpsapi.api.model.entities.Player;
import com.example.rpsapi.api.model.entities.RockPaperScissorsGame;
import com.example.rpsapi.api.repository.GameRepository;
import com.example.rpsapi.api.state.GameActiveState;
import com.example.rpsapi.api.state.GameState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    Player player = new Player("player1");

*/
/*    @Test
    public void testCreateGame() {
        RockPaperScissorsGame game = new RockPaperScissorsGame(player);

        doNothing().when(gameRepository).addGame(any(RockPaperScissorsGame.class));

        String gameId = gameService.createGame(player);

        assertEquals(game.getGameID(), gameId);
    }*//*


    @Test
    public void testGetGameState() {
        String gameId = "game1";
        RockPaperScissorsGame game = new RockPaperScissorsGame(player);
        GameState gameState = new GameActiveState(game);
        game.setState(gameState);
        when(gameRepository.getGame(gameId)).thenReturn(game);

        String result = gameService.getGameState(gameId);

        assertEquals(gameState.getState(), result);
    }

    @Test
    public void testJoinGame() {
        String gameId = "game1";
        RockPaperScissorsGame game = new RockPaperScissorsGame(player);
        when(gameRepository.getGame(gameId)).thenReturn(game);

        RockPaperScissorsGame result = gameService.joinGame(gameId, player);

        assertEquals(game, result);
    }

    @Test
    public void testMakeMoveGameNotExist() {
        String gameId = "game1";
        MoveRequest moveRequest = new MoveRequest("player1", Move.PAPER);

        when(gameRepository.getGame(gameId)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> gameService.makeMove(gameId, moveRequest));
    }

    // TODO: Add more test cases for makeMove() method
}
*/
