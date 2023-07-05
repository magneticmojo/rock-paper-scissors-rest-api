package com.magneticmojo.rpsapi.api.controller;

import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import com.magneticmojo.rpsapi.api.model.responses.CreateGameResponse;
import com.magneticmojo.rpsapi.api.state.GameState;
import com.magneticmojo.rpsapi.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

// TODO ==> FIXA TESTER
// TODO ==> Minimera error-handling
// TODO ==> SPRING BOOT ANNOTATIONER
// TODO ==> Fixa kommentarer --> Javadoc?
// TODO ==> Fixa README
// TODO ==> Catch REsponseStatusException
@RestController
@RequestMapping("/api/games")
public class GameController { // TODO @TEST

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<CreateGameResponse> createGame(@RequestBody @Validated Player player) {
        String id = gameService.createGame(player);
        CreateGameResponse response = new CreateGameResponse("NEW GAME CREATED", id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameState> getGameState(@PathVariable String id) {
        GameState gameState = gameService.getGameState(id);
        return ResponseEntity.ok(gameState);
    }

    // todo -> @Validated? @NotBlank Handling?
    @PatchMapping("/{id}/join")
    public ResponseEntity<GameState> joinGame(@PathVariable String id, @RequestBody @Validated Player player) {
        // FÅnga rätt undantag med ErrorHandler
        GameState gameState = gameService.joinGame(id, player);
        return ResponseEntity.ok(gameState);
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<GameState> makeMove(@PathVariable String id, @RequestBody @Validated PlayerMove playerMove) {
        GameState gameState = gameService.makeMove(id, playerMove);
        return ResponseEntity.ok(gameState);
    }
}
