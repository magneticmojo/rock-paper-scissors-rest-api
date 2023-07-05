package com.example.rpsapi.api.model.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// TODO -> IMPLEMENT EQUALS AND HASHCODE AND TOSTRING???
// TODO -> ANootations for validation + CLASS

public class Player {

    private String name;
    private Move move;

    // TODO --> UTAN ==> Bad REQUEST
    @JsonCreator
    public Player(@JsonProperty("name") String name) {
        this.name = name;
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

}
