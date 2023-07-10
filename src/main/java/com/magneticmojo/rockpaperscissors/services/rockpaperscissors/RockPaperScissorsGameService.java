package com.magneticmojo.rockpaperscissors.services.rockpaperscissors;

import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.exceptions.GameNotFoundException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.PlayerMoveNullException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.PlayerNullException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.RockPaperScissorsGame;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.states.RockPaperScissorsGameState;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.repository.RockPaperScissorsGameRepository;
import org.springframework.stereotype.Service;

@Service
public class RockPaperScissorsGameService {

    private final RockPaperScissorsGameRepository rockPaperScissorsGameRepository;

    public RockPaperScissorsGameService(RockPaperScissorsGameRepository rockPaperScissorsGameRepository) {
        this.rockPaperScissorsGameRepository = rockPaperScissorsGameRepository;
    }

    public String createGame(Player playerOne) {
        RockPaperScissorsGame game = new RockPaperScissorsGame(playerOne);
        addNewGame(game);
        return game.getId();
    }

    private void addNewGame(RockPaperScissorsGame game) {
        rockPaperScissorsGameRepository.addGame(game);
    }

    public RockPaperScissorsGameState getGameState(String id) {
        RockPaperScissorsGame rpsGame = getGameOrElseThrow(id);
        return rpsGame.getState();
    }

    public RockPaperScissorsGameState joinGame(String id, Player playerTwo) {
        if (playerTwo == null) {
            throw new PlayerNullException("Player cannot be null");
        }
        RockPaperScissorsGame rpsGame = getGameOrElseThrow(id);
        rpsGame.joinGame(playerTwo);
        return rpsGame.getState();
    }

    public RockPaperScissorsGameState makeMove(String id, PlayerMove playerMove) {
        if (playerMove == null) {
            throw new PlayerMoveNullException("Player move cannot be null");
        }
        RockPaperScissorsGame rpsGame = getGameOrElseThrow(id);
        rpsGame.makeMove(playerMove);
        return rpsGame.getState();
    }

    private RockPaperScissorsGame getGameOrElseThrow(String id) {
        RockPaperScissorsGame rpsGame = rockPaperScissorsGameRepository.getGame(id);
        if (rpsGame == null) {
            throw new GameNotFoundException("Invalid id: " + id);
        }
        return rpsGame;
    }
}
