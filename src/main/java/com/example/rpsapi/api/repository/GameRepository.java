package com.example.rpsapi.api.repository;

import com.example.rpsapi.api.model.entities.RPSGame;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class GameRepository {

    private final ConcurrentHashMap<String, RPSGame> gameMap = new ConcurrentHashMap<>();

    public void addGame(RPSGame game) {
        gameMap.put(game.getGameID(), game); // thread-safe
    }

    public RPSGame getGame(String id) {
        return gameMap.get(id);
    }

    public void updateGame(RPSGame game) {
        gameMap.put(game.getGameID(), game);
    }


    // make move





}
