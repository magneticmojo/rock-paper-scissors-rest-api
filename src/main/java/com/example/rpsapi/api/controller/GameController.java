package com.example.rpsapi.api.controller;

import com.example.rpsapi.api.model.dto.CreateGameRequest;
import com.example.rpsapi.api.model.dto.MoveRequest;
import com.example.rpsapi.api.model.entities.Player;
import com.example.rpsapi.api.model.entities.RPSGame;
import com.example.rpsapi.api.state.GameState;
import com.example.rpsapi.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// TODO ==> VAD SKICKAS TILLBAKA?
// TODO ==> LÄGG TILL INPUT VALIDERING
// TODO ==> FIXA DTOS???
// TODO ==> FIXA ENUM KLASSEN
// TODO ==> FIXA TESTER
// TODO ==> Minimera error-handling
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
    public ResponseEntity<String> createGame(@RequestBody Player player) { // todo -> CreateGameDTO?

        String id = gameService.createGame(player);

        // todo -> vad ska returneras här??

        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameState> getGameState(@PathVariable String id) {

        GameState gameState = gameService.getGameState(id);
        // todo -> vad ska returneras här??
        return ResponseEntity.ok(gameState);
    }

    @PatchMapping("/{id}/join")
    public ResponseEntity<GameState> joinGame(@PathVariable String id, @RequestBody Player player) { // todo -> ta join request istället för player

        GameState gameState = gameService.joinGame(id, player); // todo -> void?

        // todo -> vad ska returneras här??
        return ResponseEntity.ok(gameState);
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<GameState> makeMove(@PathVariable String id, @RequestBody MoveRequest moveRequest) {
        GameState gameState = gameService.makeMove(id, moveRequest);
        // todo -> vad ska returneras här??
        return ResponseEntity.ok(gameState); // TODO return MoveResponse DTO instead
    }
}
