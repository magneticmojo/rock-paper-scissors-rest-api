package com.magneticmojo.rockpaperscissors.api.controller;

import com.magneticmojo.rockpaperscissors.api.model.responses.GameStateResponse;
import com.magneticmojo.rockpaperscissors.api.model.responses.MoveResponse;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.RockPaperScissorsGameService;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;
import com.magneticmojo.rockpaperscissors.api.model.responses.GameCreatedResponse;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.FirstMoveMadeState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.GameEndedState;
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
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final RockPaperScissorsGameService rockPaperScissorsGameService;

    public GameController(RockPaperScissorsGameService rockPaperScissorsGameService) {
        this.rockPaperScissorsGameService = rockPaperScissorsGameService;
    }

    @PostMapping
    public ResponseEntity<GameCreatedResponse> createGame(@RequestBody @Validated Player playerOne) { // TODO PlayerRequest
        String id = rockPaperScissorsGameService.createGame(playerOne); // TODO SKA SERVICE SKAPA RESPONSE? ELLER CONTROLLER?
        GameCreatedResponse response = new GameCreatedResponse("Rock-Papper-Scissors game created", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RockPaperScissorsGameState> getGameState(@PathVariable String id) {
        RockPaperScissorsGameState rockPaperScissorsGameState = rockPaperScissorsGameService.getGameState(id);
        return ResponseEntity.ok(rockPaperScissorsGameState);
    }

    @PatchMapping("/{id}/join")
    public ResponseEntity<RockPaperScissorsGameState> joinGame(@PathVariable String id, @RequestBody @Validated Player playerTwo) { // TODO PlayerRequest
        RockPaperScissorsGameState rockPaperScissorsGameState = rockPaperScissorsGameService.joinGame(id, playerTwo);



        return ResponseEntity.ok(rockPaperScissorsGameState);
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<MoveResponse> makeMove(@PathVariable String id, @RequestBody @Validated PlayerMove playerMove) { // TODO PlayerMoveRequest
        RockPaperScissorsGameState rockPaperScissorsGameState = rockPaperScissorsGameService.makeMove(id, playerMove);

        // Move Response --> FirstMoveMadeState
        MoveResponse moveResponse = null;
        if (rockPaperScissorsGameState instanceof FirstMoveMadeState) {
            moveResponse = new MoveResponse(((FirstMoveMadeState) rockPaperScissorsGameState).getFirstPlayerMove(), "First");
        }

        if (rockPaperScissorsGameState instanceof GameEndedState) {
            moveResponse = new MoveResponse(((GameEndedState) rockPaperScissorsGameState).getLastPlayerMove(), "Last");
        }

        return ResponseEntity.ok(moveResponse);
    }
}
