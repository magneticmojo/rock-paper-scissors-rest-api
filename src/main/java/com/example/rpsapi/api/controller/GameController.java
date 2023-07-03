package com.example.rpsapi.api.controller;

import com.example.rpsapi.api.model.dto.MoveRequest;
//import com.example.rpsapi.api.model.entities.Move;
//import com.example.rpsapi.api.model.dto.PlayerDTO;
//import com.example.rpsapi.api.model.dto.RPSGameDTO;
import com.example.rpsapi.api.model.entities.Player;
import com.example.rpsapi.api.model.entities.RPSGame;
import com.example.rpsapi.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// Thread-safety
// TODO --> Spring MVC not inherently thread-safe ==> @Scope("request") ==> new instance for each request ANS:(DONT SOLVE ISSUE AS SHARED STATE IS OUTSIDE THE CONTROLLER)
// TODO --> Avoid using class varibles ==> Use method variables instead
// TODO --> LOCAL VARIABLES ARE THREAD-SAFE --> BUT NO DEPENDENCY INJECTION --> HARD TO TEST
// TODO --> Concurrency == Problem with only 2 clients?
// TODO --> ThreadLocal??? ThreadLocalRandom??
// http://dolszewski.com/spring/spring-bean-thread-safety-guide/

// Client input:
// TODO -->
// TODO -> Fråga MAX: Inget problerm att avslöja inter entitet???
// Another advantage of using DTOs on RESTful APIs written in Java (and on Spring Boot),
// is that they can help to hide implementation details of domain objects (JPA entities).
// Exposing entities through endpoints can become
// a security issue if we do not carefully handle
// what properties can be changed through what operations.


// TODO -> ENDPONTS
// TODO -> Return links to other endpoints? HATEOAS --> YES + Include in API Documentation

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    // DI ==> Spring manage service lifetime and auto-inject its dependencies
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // TODO --> Se till att det är så svårt som möjligt att göra fel ==> Minimera error-handling

    //@ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Map<String, String>> createGame(@RequestBody Player player) {
        String gameID = gameService.createGame(player);

        // todo -> return playerId
        Map<String, String> response = Map.of("gameID", gameID); // Creates an immutable map
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{gameID}")
    public ResponseEntity<RPSGame> getGameState(@PathVariable String gameID) {
        RPSGame rpsGame = gameService.getGameState(gameID);

        return ResponseEntity.ok(rpsGame);
    }

    @PatchMapping("/{gameId}/join")
    public ResponseEntity<RPSGame> joinGame(@PathVariable String gameId, @RequestBody Player player) {
        RPSGame rpsGame = gameService.joinGame(gameId, player);
        return ResponseEntity.ok(rpsGame);
    }

    // todo @params gameId, @RequestBody playerID, move
    @PatchMapping("/{gameID}/move")
    public String makeMove(@PathVariable String gameID, @RequestBody MoveRequest moveRequest) { // Spring binds JSON to DTO
        String playerMessage = gameService.makeMove(gameID, moveRequest);
        return ResponseEntity.ok(playerMessage).toString(); // TODO return MoveResponse DTO instead
    }
}
