package com.example.rpsapi.service;

import com.example.rpsapi.api.exception.GameNotFoundException;
import com.example.rpsapi.api.model.entities.Player;
import com.example.rpsapi.api.model.entities.PlayerMove;
import com.example.rpsapi.api.model.entities.RockPaperScissorsGame;
import com.example.rpsapi.api.repository.GameRepository;
import com.example.rpsapi.api.state.GameState;
import org.springframework.stereotype.Service;

// TODO -> GameRepository -> SHOULD BE THREAD SAFE --> Field not modified after construction + ConcurrentHashmap + AtomicInteger(Change to UUID)
// TODO -> GameService Interface + GameServiceImpl???
@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    // POST /api/games
    public String createGame(Player player) {

/*        if (isInValidPlayerInput(player)) {
            throw new IllegalArgumentException("Player not valid");
        }*/

        RockPaperScissorsGame game = new RockPaperScissorsGame(player);
        gameRepository.addGame(game);
        return game.getGameID(); // todo -> ToString || ToJson || UUID
    }

/*    private boolean isInValidPlayerInput(Player player) { // todo -> Bad naming
        // todo -> hasMove
        return player == null || player.name() == null || player.name().isBlank(); // todo -> isEMpty()?
    }*/

    // GET /api/games/{id}
    public GameState getGameState(String id) {
        RockPaperScissorsGame rpsGame = getGame(id);
        return rpsGame.getState();
    }

    private RockPaperScissorsGame getGame(String id) throws IllegalArgumentException, GameNotFoundException { // todo -> BAD NAMING || getGameOrThrow
/*        if (isInvalidGameID(id)) {
            throw new IllegalArgumentException("Game ID invalid");
        }*/

        RockPaperScissorsGame rpsGame = gameRepository.getGame(id);

        if (rpsGame == null) {
            throw new GameNotFoundException("Game does not exist. Wrong ID?"); // todo -> IMPROPER MESSAGING?
        }
        return rpsGame;
    }
/*
    private boolean isInvalidGameID(String id) {
        return id == null || id.isBlank(); // todo -> isEMpty()?
    }*/

    // PATCH /api/games/{gameId}/join
    public GameState joinGame(String id, Player playerTwo) {

/*        if (isInValidPlayerInput(playerTwo)) { // todo -> DUPLICATION
            throw new IllegalArgumentException("Player not valid");
        }*/

        RockPaperScissorsGame rpsGame = getGame(id);
        return rpsGame.joinGame(playerTwo);
    }

    // PATCH /api/games/{id}/move
    public GameState makeMove(String id, PlayerMove playerMove) {

/*        if (isInValidPlayerInput(playerMove.player())) {
            throw new IllegalArgumentException("Player not valid XXXXXX");
        }*/

        RockPaperScissorsGame rpsGame = getGame(id);

        return rpsGame.makeMove(playerMove);
    }




}
