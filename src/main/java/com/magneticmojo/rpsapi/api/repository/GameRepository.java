package com.magneticmojo.rpsapi.api.repository;

import com.magneticmojo.rpsapi.api.model.entities.RockPaperScissorsGame;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class GameRepository {

    private final ConcurrentHashMap<String, RockPaperScissorsGame> gameMap = new ConcurrentHashMap<>();

    public void addGame(RockPaperScissorsGame game) {
        gameMap.put(game.getId(), game);
    }

    public RockPaperScissorsGame getGame(String id) {
        return gameMap.get(id);
    }
}
