package com.example.rpsapi.api.repository;

import com.example.rpsapi.api.model.entities.RockPaperScissorsGame;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class GameRepository {

    private final ConcurrentHashMap<String, RockPaperScissorsGame> gameMap = new ConcurrentHashMap<>();

    public void addGame(RockPaperScissorsGame game) {
        gameMap.put(game.getGameID(), game); // thread-safe
    }

    public RockPaperScissorsGame getGame(String id) {
        return gameMap.get(id);
    }

    public void updateGame(RockPaperScissorsGame game) {
        gameMap.put(game.getGameID(), game);
    }

    // make move
}
