package com.example.rpsapi.api.model.entities;

public class Player {
    private String name;
    private Move move;

    public Player(String name) {
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
