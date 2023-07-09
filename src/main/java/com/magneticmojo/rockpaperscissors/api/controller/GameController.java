package com.magneticmojo.rockpaperscissors.api.controller;

import com.magneticmojo.rockpaperscissors.api.model.requests.MakeMoveRequest;
import com.magneticmojo.rockpaperscissors.api.model.requests.PlayerRequest;
import com.magneticmojo.rockpaperscissors.api.model.responses.JoinGameResponse;
import com.magneticmojo.rockpaperscissors.api.model.responses.MakeMoveResponse;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.RockPaperScissorsGameService;
import com.magneticmojo.rockpaperscissors.api.model.responses.CreateGameResponse;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.FirstMoveMadeState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.GameEndedState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.PlayerTwoJoinedState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.RockPaperScissorsGameState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * GameController is the REST API controller for the Rock-Paper-Scissors game. It handles all incoming HTTP requests.
 * The controller delegates the processing of these requests to the RockPaperScissorsGameService.
 * <p>
 * Endpoints include:
 * 1) POST /api/games: Creates a new game and returns the game ID.
 * 2) GET /api/games/{id}: Retrieves the current state of the game with the specified ID.
 * 3) PATCH /api/games/{id}/join: Allows a second player to join an existing game.
 * 4) PATCH /api/games/{id}/move: Enables players to make their moves in the game.
 * <p>
 * All the API responses are encapsulated in ResponseEntity objects with HTTP status codes.
 */

// TODO id -> gameId??? Känns tydligare
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final RockPaperScissorsGameService rockPaperScissorsGameService;

    public GameController(RockPaperScissorsGameService rockPaperScissorsGameService) {
        this.rockPaperScissorsGameService = rockPaperScissorsGameService;
    }

    @PostMapping
    public ResponseEntity<CreateGameResponse> createGame(@RequestBody @Validated PlayerRequest playerRequest) {

         // TODO REQUEST OBJECT PlayerRequest
        String id = rockPaperScissorsGameService.createGame(playerRequest.name());

        // TODO Serializer -> CreateGameResponse
        // TODO @NotNull needed?
        CreateGameResponse response = new CreateGameResponse(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RockPaperScissorsGameState> getGameState(@PathVariable String id) {
        RockPaperScissorsGameState rockPaperScissorsGameState = rockPaperScissorsGameService.getGameState(id);

        // TODO RESPONSE OBJEKT -> Men för olika states?????
        return ResponseEntity.status(HttpStatus.OK).body(rockPaperScissorsGameState);
    }

    @PatchMapping("/{id}/join") // (todo) DONE!
    public ResponseEntity<JoinGameResponse> joinGame(@PathVariable String id, @RequestBody @Validated PlayerRequest playerRequest) {
        PlayerTwoJoinedState playerTwoJoinedState = (PlayerTwoJoinedState) rockPaperScissorsGameService.joinGame(id, playerRequest.name());
        JoinGameResponse joinGameResponse = new JoinGameResponse(playerTwoJoinedState.getPlayerTwo(), "Two", id);
        return ResponseEntity.ok(joinGameResponse);
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<MakeMoveResponse> makeMove(@PathVariable String id, @RequestBody @Validated MakeMoveRequest makeMoveRequest) {
        // TODO Nested validation
        // TODO -> VAR SKA VALIDERINGEN SKE?

        RockPaperScissorsGameState rockPaperScissorsGameState = rockPaperScissorsGameService.makeMove(id, makeMoveRequest.playerName(), makeMoveRequest.move());

        MakeMoveResponse makeMoveResponse = null;
        if (rockPaperScissorsGameState instanceof FirstMoveMadeState) {
            makeMoveResponse = new MakeMoveResponse(((FirstMoveMadeState) rockPaperScissorsGameState).getFirstPlayerMove(), "First", id);
        }

        if (rockPaperScissorsGameState instanceof GameEndedState) {
            makeMoveResponse = new MakeMoveResponse(((GameEndedState) rockPaperScissorsGameState).getLastPlayerMove(), "Last", id);
        }

        return ResponseEntity.ok(makeMoveResponse);
    }
}
