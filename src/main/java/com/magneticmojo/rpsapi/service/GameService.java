package com.magneticmojo.rpsapi.service;

import com.magneticmojo.rpsapi.api.exception.GameNotFoundException;
import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import com.magneticmojo.rpsapi.api.model.entities.RockPaperScissorsGame;
import com.magneticmojo.rpsapi.api.repository.GameRepository;
import com.magneticmojo.rpsapi.api.state.GameState;
import org.springframework.stereotype.Service;

@Service
public class GameService { // TODO @TEST

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    // POST /api/games
    public String createGame(Player player) {
        RockPaperScissorsGame game = new RockPaperScissorsGame(player);
        gameRepository.addGame(game);
        return game.getId();
    }

    // GET /api/games/{id}
    public GameState getGameState(String id) {
        RockPaperScissorsGame rpsGame = getGame(id);
        return rpsGame.getState();
    }

    private RockPaperScissorsGame getGame(String id) {
        RockPaperScissorsGame rpsGame = gameRepository.getGame(id);
        if (rpsGame == null) {
            throw new GameNotFoundException("Invalid id");
        }
        return rpsGame;
    }

    // PATCH /api/games/{gameId}/join
    public GameState joinGame(String id, Player playerTwo) {
        RockPaperScissorsGame rpsGame = getGame(id);
        return rpsGame.joinGame(playerTwo);
    }

    // PATCH /api/games/{id}/move
    public GameState makeMove(String id, PlayerMove playerMove) {
        RockPaperScissorsGame rpsGame = getGame(id);
        return rpsGame.makeMove(playerMove);
    }
}
