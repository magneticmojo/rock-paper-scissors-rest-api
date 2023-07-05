package com.example.rpsapi.api.controller;

import com.example.rpsapi.api.model.entities.Player;
import com.example.rpsapi.api.model.entities.PlayerMove;
import com.example.rpsapi.api.state.GameState;
import com.example.rpsapi.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;


// JACKSON  Läs

// TODO ==> VAD SKICKAS TILLBAKA?
// TODO ==> LÄGG TILL INPUT VALIDERING
// TODO ==> FIXA DTOS???
// TODO ==> FIXA ENUM KLASSEN
// TODO ==> HANTERA INPUT ENUM
// TODO ==> FIXA TESTER
// TODO ==> Minimera error-handling
// TODO ==> SPRING BOOT ANNOTATIONER
// TODO ==> Fixa kommentarer
// TODO ==> Fixa README
// TODO ==> INSTRUKTIONER
// TODO ==> JAVADOC?????
@RestController
@RequestMapping("/api/games") // todo -> Är detta samma som för create game??
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<String> createGame(@RequestBody @Validated Player player) {

        String id = gameService.createGame(player);

        // todo -> vad ska returneras här??

        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameState> getGameState(@PathVariable String id) {

        if (isInvalidIDFormat(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id format");
        }

        GameState gameState = gameService.getGameState(id);
        // todo -> vad ska returneras här??
        return ResponseEntity.ok(gameState);
    }

    private boolean isInvalidIDFormat(String id) {
        if (id == null) {
            return true;
        }
        try {
            UUID.fromString(id); // todo -> THROW IF CAN'T CREATE -> UGLY SOLUTION???
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    @PatchMapping("/{id}/join")
    public ResponseEntity<GameState> joinGame(@PathVariable String id, @RequestBody @Validated Player player) { // todo -> @Validated? @NotBlank Handling

        GameState gameState = gameService.joinGame(id, player);

        // todo -> vad ska returneras här??
        return ResponseEntity.ok(gameState);
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<GameState> makeMove(@PathVariable String id, @RequestBody @Validated PlayerMove playerMove) {
        GameState gameState = gameService.makeMove(id, playerMove);
        // todo -> vad ska returneras här??
        return ResponseEntity.ok(gameState);
    }
}
