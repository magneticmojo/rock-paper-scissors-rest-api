package com.magneticmojo.rpsapi.api.controller;

import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import com.magneticmojo.rpsapi.api.model.responses.GameCreatedResponse;
import com.magneticmojo.rpsapi.api.state.GameState;
import com.magneticmojo.rpsapi.api.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * GameController is the REST API controller for the Rock-Paper-Scissors game. It handles all incoming HTTP requests.
 * The controller delegates the processing of these requests to the GameService.
 * <p>
 * Endpoints include:
 * 1) POST /api/games: Creates a new game and returns the game ID.
 * 2) GET /api/games/{id}: Retrieves the current state of the game with the specified ID.
 * 3) PATCH /api/games/{id}/join: Allows a second player to join an existing game.
 * 4) PATCH /api/games/{id}/move: Enables players to make their moves in the game.
 * <p>
 * All the API responses are encapsulated in ResponseEntity objects with HTTP status codes.
 */
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<GameCreatedResponse> createGame(@RequestBody @Validated Player playerOne) {
        String id = gameService.createGame(playerOne);
        GameCreatedResponse response = new GameCreatedResponse("Rock-Papper-Scissors game created", id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameState> getGameState(@PathVariable String id) {
        GameState gameState = gameService.getGameState(id);
        return ResponseEntity.ok(gameState);
    }

    @PatchMapping("/{id}/join")
    public ResponseEntity<GameState> joinGame(@PathVariable String id, @RequestBody @Validated Player playerTwo) {
        GameState gameState = gameService.joinGame(id, playerTwo);
        return ResponseEntity.ok(gameState);
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<GameState> makeMove(@PathVariable String id, @RequestBody @Validated PlayerMove playerMove) {
        GameState gameState = gameService.makeMove(id, playerMove);
        return ResponseEntity.ok(gameState);
    }
}
