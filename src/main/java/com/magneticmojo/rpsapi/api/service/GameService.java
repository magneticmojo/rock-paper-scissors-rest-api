package com.magneticmojo.rpsapi.api.service;

import com.magneticmojo.rpsapi.api.exceptions.gameexception.GameNotFoundException;
import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
import com.magneticmojo.rpsapi.api.model.entities.RockPaperScissorsGame;
import com.magneticmojo.rpsapi.api.repository.GameRepository;
import com.magneticmojo.rpsapi.api.state.GameState;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public String createGame(Player playerOne) {
        RockPaperScissorsGame game = new RockPaperScissorsGame(playerOne);
        addNewGame(game);
        return game.getId();
    }

    private void addNewGame(RockPaperScissorsGame game) {
        gameRepository.addGame(game);
    }

    public GameState getGameState(String id) {
        RockPaperScissorsGame rpsGame = getGameOrElseThrow(id);
        return rpsGame.getState();
    }

    public GameState joinGame(String id, Player playerTwo) {
        RockPaperScissorsGame rpsGame = getGameOrElseThrow(id);
        return rpsGame.joinGame(playerTwo);
    }

    public GameState makeMove(String id, PlayerMove playerMove) {
        RockPaperScissorsGame rpsGame = getGameOrElseThrow(id);
        return rpsGame.makeMove(playerMove);
    }

    private RockPaperScissorsGame getGameOrElseThrow(String id) {
        RockPaperScissorsGame rpsGame = gameRepository.getGame(id);
        if (rpsGame == null) {
            throw new GameNotFoundException("Invalid id: ", id);
        }
        return rpsGame;
    }
}
