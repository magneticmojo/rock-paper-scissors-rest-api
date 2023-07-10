package com.magneticmojo.rockpaperscissors.api.controller;

import com.magneticmojo.rockpaperscissors.api.model.responses.gameresponses.JoinGameResponse;
import com.magneticmojo.rockpaperscissors.api.model.responses.gameresponses.MakeMoveResponse;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.RockPaperScissorsGameService;
import com.magneticmojo.rockpaperscissors.api.model.responses.gameresponses.CreateGameResponse;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.FirstMoveMadeState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.GameEndedState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.PlayerTwoJoinedState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.RockPaperScissorsGameState;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * RockPaperScissorsGameController is the REST API controller for the Rock-Paper-Scissors game. It handles all incoming HTTP requests.
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
public class RockPaperScissorsGameController {

    private final RockPaperScissorsGameService rockPaperScissorsGameService;

    public RockPaperScissorsGameController(RockPaperScissorsGameService rockPaperScissorsGameService) {
        this.rockPaperScissorsGameService = rockPaperScissorsGameService;
    }

    @PostMapping
    public ResponseEntity<CreateGameResponse> createGame(@RequestBody @Validated Player playerOne) {
        String id = rockPaperScissorsGameService.createGame(playerOne);
        CreateGameResponse response = new CreateGameResponse(id, playerOne);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RockPaperScissorsGameState> getGameState(@PathVariable String id) {
        RockPaperScissorsGameState gameState = rockPaperScissorsGameService.getGameState(id);
        return ResponseEntity.ok(gameState);
    }

    @PatchMapping("/{id}/join")
    public ResponseEntity<JoinGameResponse> joinGame(@PathVariable String id, @RequestBody @Validated Player playerTwo) {
        PlayerTwoJoinedState gameState = (PlayerTwoJoinedState) rockPaperScissorsGameService.joinGame(id, playerTwo);
        JoinGameResponse response = new JoinGameResponse(gameState.getPlayerTwo());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<MakeMoveResponse> makeMove(@PathVariable String id, @RequestBody @Validated PlayerMove playerMove) {
        RockPaperScissorsGameState gameState = rockPaperScissorsGameService.makeMove(id, playerMove);

        MakeMoveResponse response = null;
        if (gameState instanceof FirstMoveMadeState) {
            response = new MakeMoveResponse(((FirstMoveMadeState) gameState).getFirstPlayerMove(), "First");
        }

        if (gameState instanceof GameEndedState) {
            response = new MakeMoveResponse(((GameEndedState) gameState).getLastPlayerMove(), "Last");
        }

        return ResponseEntity.ok(response);
    }
}

// För alla endpoints --> Korrekt input & inkorrekt input
// TODO POST:
// TODO CREATE GAME --> Correct player NotBlank (no validation fail) + 201
// TODO CREATE GAME --> Incorrect Input Null && Blank player name (Validation fail) + 400 (Bad Request) --> Vad ger @Validated
// TODO CREATE GAME --> Correct Output
// TODO CREATE GAME --> Correct State

// TODO GET:
// TODO GET GAME STATE --> Correct id (no validation fail) + 200
// TODO GET GAME STATE --> Incorrect id -> GameNotFoundException + 404???
// TODO GET GAME STATE --> Gamestate = PlayerOneJoinedState
//
// TODO PATCH (Join Game)
// TODO JOIN GAME --> Correct Input (no validation fail) + 200
// TODO JOIN GAME --> Incorrect Input - Null && Blank player name (Validation fail) + 400 (Bad Request) --> Vad ger @Validated
// TODO JOIN GAME --> Incorrect id - GameNotFoundException + 404
// TODO JOIN GAME --> Gamestate = PlayerTwoJoinedState

// TODO PATCH (Make Move)
// TODO MAKE MOVE --> Correct Input (no validation fail) + 200
// TODO MAKE MOVE --> Incorrect Input - Null && Blank player name (Validation fail) + 400 (Bad Request) --> Vad ger @Validated
// TODO MAKE MOVE --> PlayerMove = null --> ???
// TODO MAKE MOVE --> PlayerMove = new PlayerMove(player, null) --> ???
// TODO MAKE MOVE --> PlayerMove = new PlayerMove(player, Move.ROCK) --> ???
// TODO MAKE MOVE --> Setup Create + Join --> Make Move --> Gamestate = FirstMoveMadeState --> Make Move --> Gamestate = GameEndedState

// TODO MAKE MOVE --> Incorrect id - GameNotFoundException + 404
// TODO MAKE MOVE --> PlayerOneJoinedState --> throw new MissingPlayerTwoException("Move prohibited. Player two not joined");
// TODO MAKE MOVE --> PlayerTwoJoinedState --> throw new PlayerNotInGameException("Player not in game. Cannot make move");
// TODO MAKE MOVE --> FirstMoveMadeState --> throw new PlayerNotInGameException("Player not in game. Cannot make move");
// TODO MAKE MOVE --> FirstMoveMadeState --> throw new MultipleMovesProhibitedException("Player already made move. Cannot make another move");
// TODO MAKE MOVE --> GameEndedState --> throw new GameEndedException("Game ended. Cannot make move");




