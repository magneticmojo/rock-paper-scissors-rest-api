package com.magneticmojo.rpsapi.api.controller;

import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import com.magneticmojo.rpsapi.api.model.responses.GameCreatedResponse;
import com.magneticmojo.rpsapi.api.state.GameState;
import com.magneticmojo.rpsapi.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
public class GameController { // TODO @TEST

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<GameCreatedResponse> createGame(@RequestBody @Validated Player playerOne) {
        String id = gameService.createGame(playerOne);
        GameCreatedResponse response = new GameCreatedResponse("New ROCK-PAPER-SCISSORS-GAME created", id);
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
