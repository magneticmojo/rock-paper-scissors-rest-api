package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.repository;

import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.RockPaperScissorsGame;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryRockPaperScissorsGameRepository implements RockPaperScissorsGameRepository{

    private final ConcurrentHashMap<String, RockPaperScissorsGame> gameMap = new ConcurrentHashMap<>();

    public void addGame(RockPaperScissorsGame game) {
        gameMap.put(game.getId(), game);
    }

    public RockPaperScissorsGame getGame(String id) {
        return gameMap.get(id);
    }
}
