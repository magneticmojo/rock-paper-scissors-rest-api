package com.example.rpsapi.service;

import com.example.rpsapi.api.model.dto.PlayerDTO;
import com.example.rpsapi.api.model.dto.RPSGameDTO;
import com.example.rpsapi.api.model.entities.Player;
import com.example.rpsapi.api.model.entities.RPSGame;
import com.example.rpsapi.api.repository.GameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private GameRepository gameRepository;
    private MessageService messageService;

    public GameService(GameRepository gameRepository, MessageService messageService) {
        this.gameRepository = gameRepository;
        this.messageService = messageService;
    }

    public int createGame(PlayerDTO playerDTO) {
        Player player1 = new Player(playerDTO.name());
        RPSGame game = new RPSGame(player1);
        int id = gameRepository.addGame(game); // addGame() returns int -> id
        game.setId(id);
        return id;
    }

    public RPSGameDTO getGameState(int id) {
        // todo -> return error if game does not exist
        RPSGame rpsGame = gameRepository.getGame(id);
        return convertToGameDTO(rpsGame);
    }

    // todo -> rename
    private RPSGameDTO convertToGameDTO(RPSGame rpsGame) {
        // todo -> Get values of RPSGame and put in Map -> return Map

        if (rpsGame == null) {
            throw new NullPointerException("Game does not exist");
        }

        String id = String.valueOf(rpsGame.getId());

        String player1 = rpsGame.getPlayer1().getName();
        String player2;

        if (rpsGame.getPlayer2() != null) {
            player2 = rpsGame.getPlayer2().getName();
        }
        else {
            player2 = messageService.getMessage("no_player_2");
        }

        return new RPSGameDTO(id, player1, player2);
    }

    // todo -> Requirements !state return value
    public RPSGameDTO joinGame(int id, PlayerDTO player2DTO) {
        // todo -> return error if game does not exist
        // todo -> return error if player2 already exists
        RPSGame rpsGame = gameRepository.getGame(id);
        if (rpsGame != null) {
            Player player2 = new Player(player2DTO.name());
            rpsGame.setPlayer2(player2);
            gameRepository.updateGame(rpsGame);
        }

        return convertToGameDTO(rpsGame); // todo -> does it convert correctly?
    }

    public RPSGameDTO makeMove(int id, PlayerDTO playerDTO) {
        // TODO --> Vill behöva kolla så få grejer som möjligt

        // some logic
        // chack if game exists
        RPSGame rpsGame = gameRepository.getGame(id);

        if (rpsGame == null) {
            throw new NullPointerException("Game does not exist");
        }

        // check if player exists in game
        if (!rpsGame.hasPlayer(playerDTO.name())) {
            throw new NullPointerException("Player does not exist in game");
        }

        // check if player has made a move




        // check if both players have made a move

        // pass back to repository with updated player move

        return null;
    }




}
