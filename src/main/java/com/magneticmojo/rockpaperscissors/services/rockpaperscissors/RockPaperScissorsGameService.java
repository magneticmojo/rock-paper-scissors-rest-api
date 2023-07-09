package com.magneticmojo.rockpaperscissors.services.rockpaperscissors;

import com.magneticmojo.rockpaperscissors.api.model.requests.PlayerRequest;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.exceptions.GameNotFoundException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Move;
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

    public String createGame(String playerOneName) {
        Player playerOne = new Player(playerOneName);
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

    public RockPaperScissorsGameState joinGame(String id, String playerTwoName) {
        RockPaperScissorsGame rpsGame = getGameOrElseThrow(id);
        Player playerTwo = new Player(playerTwoName);
        rpsGame.joinGame(playerTwo);
        return rpsGame.getState();
    }

    public RockPaperScissorsGameState makeMove(String id, String playerName, String move) {
        RockPaperScissorsGame rpsGame = getGameOrElseThrow(id);
        PlayerMove playerMove = new PlayerMove(new Player(playerName), Move.valueOf(move));
        rpsGame.makeMove(playerMove);
        return rpsGame.getState();
    }

    private RockPaperScissorsGame getGameOrElseThrow(String id) {
        RockPaperScissorsGame rpsGame = rockPaperScissorsGameRepository.getGame(id);
        if (rpsGame == null) {
            throw new GameNotFoundException("Invalid id: ", id);
        }
        return rpsGame;
    }
}
