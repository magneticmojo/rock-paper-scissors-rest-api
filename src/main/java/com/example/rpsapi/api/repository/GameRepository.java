package com.example.rpsapi.api.repository;

import com.example.rpsapi.api.model.entities.RPSGame;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class GameRepository {

    // TODO ÄR SPRING MVC THREAD SAFE?? ELLER KÖRS MULTITRÅDAT???
    private final ConcurrentHashMap<Integer, RPSGame> gameMap = new ConcurrentHashMap<>();
    //private ConcurrentSkipListMap<String, RPSGame> gameList = new ConcurrentSkipListMap<>(); // ==> sorted

    // todo -> Randomize id??
    private final AtomicInteger idCounter = new AtomicInteger(0);

    // todo -> UUID.randomUUID().toString() -> id

    // add game -> store separate game objects in a map
    public int addGame(RPSGame game) {
        int id = idCounter.getAndIncrement(); // ret previous value then increment
        gameMap.put(id, game);
        return id;
    }

    // get game
    public RPSGame getGame(int id) {
        return gameMap.get(id);
    }


    // join game
    // todo -> ret boolean good practice?
    public boolean updateGame(RPSGame game) {
        gameMap.put(game.getId(), game);
        return true;
    }


    // make move





}
