package com.example.rpsapi.api.controller;

import com.example.rpsapi.api.model.dto.PlayerDTO;
import com.example.rpsapi.api.model.dto.RPSGameDTO;
import com.example.rpsapi.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// TODO --> Spring MVC not inherently thread-safe ==> @Scope("request") ==> new instance for each request
// TODO --> Avoid using class varibles ==> Use method variables instead
// TODO --> LOCAL VARIABLES ARE THREAD-SAFE --> BUT NO DEPENDENCY INJECTION --> HARD TO TEST

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // TODO --> Se till att det är så svårt som möjligt att göra fel ==> Minimera error-handling

    @PostMapping
    public ResponseEntity<Map<String, Integer>> createGame(@RequestBody PlayerDTO playerDTO) {
        // todo -> How User add name in request body?
        // todo -> JSON "id": 0  || --> "id": "1" (requirements == "id": "some-game-id")
        // RestController ==> Controller & ResponseBody ==> put ID in response body
        // todo -> PLayer id? --> PlayerDTO
        int id = gameService.createGame(playerDTO);
        Map<String, Integer> response = Map.of("id", id); // Creates an immutable map
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RPSGameDTO> getGameState(@PathVariable int id) {
        RPSGameDTO rpsGameDTO = gameService.getGameState(id);
        return ResponseEntity.ok(rpsGameDTO);
    }

    @PatchMapping("/{id}/join")
    public ResponseEntity<RPSGameDTO> joinGame(@RequestBody PlayerDTO player2, @PathVariable int id) {
        RPSGameDTO rpsGameDTO = gameService.joinGame(id, player2);
        return ResponseEntity.ok(rpsGameDTO);
    }


    /*
    * This endpoint is for making a move in a game, which is an action that modifies the state of a game resource. In this case, the player identified by the "name" is changing their "move" within the game identified by the id.

You could use POST, PUT, or PATCH, but each serves different purposes:

POST is typically used to create a new resource. If you consider each move as a new resource, then POST could be plausible.

PUT is used to replace a resource entirely. Unless the move you're making completely replaces the game state (which it likely doesn't), PUT would not be the best choice.

PATCH is used to apply partial modifications to a resource. You're only changing part of the game resource (a player's move), so PATCH could be appropriate.

However, in the context of a game, making a move could also be seen as creating a new "move" resource, in which case a POST could be a better option. This is more semantically correct if you are logging all moves as separate resources in the game.

Here is a more detailed explanation:

Use POST if you see the move as creating a new game state, especially if you're keeping a history of all game states.

Use PATCH if you see the move as modifying the current game state and you're not keeping a history of all game states.

Ultimately, the decision between POST and PATCH depends on how you're modeling your game and its states.
    * */

    @PatchMapping("/{id}/move")
    public String makeMove(@PathVariable String id) {
        // todo -> Fundera på vad du gör -> Safe / Idempotent?
        //Makes a move in a given game. Enter move in the request-body:
        //{
        // "name": "Pelle",
        //  "move": "ROCK"
        //}

        return "PATHC(?): Move, Name and Move in req body, ID: " + id;
    }
}
