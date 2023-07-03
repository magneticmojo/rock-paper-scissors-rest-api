package com.example.rpsapi.service;

import com.example.rpsapi.api.model.dto.MoveRequest;
import com.example.rpsapi.api.model.entities.Move;
import com.example.rpsapi.api.model.entities.Player;
import com.example.rpsapi.api.model.entities.RPSGame;
import com.example.rpsapi.api.repository.GameRepository;
import org.springframework.stereotype.Service;

// TODO -> GameRepository -> SHOULD BE THREAD SAFE --> Field not modified after construction + ConcurrentHashmap + AtomicInteger(Change to UUID)
// TODO -> GameService Interface + GameServiceImpl???
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final MessageService messageService;

    public GameService(GameRepository gameRepository, MessageService messageService) {
        this.gameRepository = gameRepository;
        this.messageService = messageService;
    }

    public String createGame(Player player) {
        RPSGame game = new RPSGame(player);
        gameRepository.addGame(game);
        return game.getGameID();
    }

    public RPSGame getGameState(String gameId) {
        return gameRepository.getGame(gameId);
    }

    public RPSGame joinGame(String gameId, Player player) {
        RPSGame rpsGame = gameRepository.getGame(gameId);
        if (rpsGame != null && (!rpsGame.isGameActive() || !rpsGame.isGameFinished())) {
            rpsGame.addPlayer(player);
            gameRepository.updateGame(rpsGame);
        }

        return rpsGame;
    }

    public String makeMove(String gameID, MoveRequest moveRequest) {
        String playerID = moveRequest.playerID();
        Move move = moveRequest.move();

        RPSGame rpsGame = gameRepository.getGame(gameID);

        if (rpsGame == null) {
            throw new NullPointerException("Game does not exist");
        }

        if (!rpsGame.hasPlayer(playerID)) {
            throw new IllegalStateException("Player is not in game"); // TODO -> JOIM GAME FIRST (PLAYER 2)
        }

        if (!rpsGame.isGameActive()) {
            throw new IllegalStateException("Game is inactive"); // TODO -> PLAYER TWO MISSING (PLAYER 1)
        }

        if (rpsGame.isGameFinished()) {
            throw new IllegalStateException("Game is finished"); // TODO -> GAME IS FINISHED
        }
        /*
        if (rpsGame.hasBothPlayersMadeMove()) {
            throw new IllegalStateException("Both players have already made a move"); // == GAME IS FINISHED
        }*/

        // TODO -> MessageService + MoveResponse
        if (rpsGame.hasPlayer(playerID)) { // IF IN GAME

            if (rpsGame.hasNoPlayerMadeMove()) { // IF NO PLAYER HAS MADE A MOVE

                if (rpsGame.isPlayerOne(playerID)) { // IF PLAYER 1
                    Player player = rpsGame.getPlayerBy(playerID); // FIRST MOVE
                    player.setMove(move);
                    return "PLAYER 1 MOVE MADE"; // todo -> return PLAYER 1 MOVE MADE
                } else {
                    return "NOT PLAYER 2 TURN"; // todo -> return NOT PLAYER 2 TURN
                }
            }

            if (rpsGame.isPlayerOne(playerID) && rpsGame.hasPlayerMadeMove(playerID)) {
                return "NOT PLAYER 1 TURN"; // todo -> return NOT PLAYER 1 TURN
            } else {
                Player player = rpsGame.getPlayerBy(playerID); // SECOND MOVE
                player.setMove(move);
                rpsGame.setGameActive(false);
                rpsGame.setGameFinished(true); // getWinner possible to call
                rpsGame.setWinner();
                return "PLAYER 2 MOVE MADE"; // todo -> return PLAYER 2 MOVE MADE
            }
        } else {
            return "PLAYER NOT IN GAME"; // todo -> return PLAYER NOT IN GAME ******** Redan kollat ovan
        }
    }




}
