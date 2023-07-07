package com.magneticmojo.rpsapi.api.model.entities;

/**
 * Enum representing the moves in the game of Rock, Paper, Scissors.
 * <p>
 * The order of the constants (ROCK, PAPER, SCISSORS) is critical for the game's logic. The order follows
 * the game's traditional rules: ROCK beats SCISSORS, SCISSORS beat PAPER, and PAPER beats ROCK.
 * <p>
 * Do not change the order of these constants as it will impact the game logic.
 * MoveTest.java contains a test for asserting the correct order.
 * <p>
 * Each move can calculate whether it wins against or is tied with another move.
 */
public enum Move {
    ROCK,
    PAPER,
    SCISSORS;

    private final static int WINNING_CONDITION = 1;

    public boolean isWinnerAgainst(Move move) {
        return WINNING_CONDITION == calculateCyclicRelation(move);
    }

    public boolean isTied(Move move) {
        return this == move;
    }

    private int calculateCyclicRelation(Move move) {
        return (values().length + ordinal() - move.ordinal()) % values().length;
    }
}
