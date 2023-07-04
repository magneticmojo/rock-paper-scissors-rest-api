package com.example.rpsapi.service;

import com.example.rpsapi.api.exception.GameNotFoundException;
import com.example.rpsapi.api.model.dto.MoveRequest;
import com.example.rpsapi.api.model.entities.Move;
import com.example.rpsapi.api.model.entities.Player;
import com.example.rpsapi.api.model.entities.RPSGame;
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

        if (isInValidPlayerInput(player)) {
            throw new IllegalArgumentException("Player not valid");
        }

        if (player.hasMove()) {
            throw new IllegalArgumentException("Player move not permitted. Missing player two");
        }

        RPSGame game = new RPSGame(player); // Sets State GameCreatedState
        gameRepository.addGame(game);
        return game.getGameID();
    }

    private boolean isInValidPlayerInput(Player player) {
        // todo -> hasMove
        return player == null || player.getName() == null || player.getName().isBlank(); // todo -> isEMpty()?
    }

    // GET /api/games/{id}
    public GameState getGameState(String id) {
        RPSGame rpsGame = getGame(id);
        System.out.println(rpsGame.getState());
        return rpsGame.getState();

    }

    // PATCH /api/games/{gameId}/join
    public GameState joinGame(String id, Player playerTwo) {

        RPSGame rpsGame = getGame(id);

        if (isInValidPlayerInput(playerTwo)) { // todo -> DUPLICATION
            throw new IllegalArgumentException("Player not valid");
        }

        if (playerTwo.hasMove()) { // todo -> DUPLICATION
            throw new IllegalArgumentException("Player move not permitted. Missing player two");
        }

        return rpsGame.joinGame(playerTwo);
    }

    private RPSGame getGame(String id) throws IllegalArgumentException, GameNotFoundException { // todo -> BAD NAMING
        if (isInvalidGameID(id)) {
            throw new IllegalArgumentException("Game ID invalid");
        }

        RPSGame rpsGame = gameRepository.getGame(id);

        if (rpsGame == null) {
            throw new GameNotFoundException("Game does not exist");
        }
        return rpsGame;
    }

    private boolean isInvalidGameID(String id) {
        return id == null || id.isBlank(); // todo -> isEMpty()?
    }

    // PATCH /api/games/{id}/move
    public GameState makeMove(String id, MoveRequest moveRequest) {

        RPSGame rpsGame = getGame(id);

        // todo --> null control
        String playerName = moveRequest.playerName();
        Move move = moveRequest.move();

        return rpsGame.makeMove(playerName, move);
    }




}
