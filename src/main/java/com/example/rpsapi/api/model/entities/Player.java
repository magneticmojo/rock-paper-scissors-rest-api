package com.example.rpsapi.api.model.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Player {

    private final String playerID;
    private String name;
    private Move move;
    private int playerNumber;

    @JsonCreator
    public Player(@JsonProperty("name") String name) {
        this.playerID = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getPlayerID() {
        return playerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public boolean hasMove() {
        return move != null;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    // TODO -> IMPLEMENT EQUALS AND HASHCODE AND TOSTRING???
}
