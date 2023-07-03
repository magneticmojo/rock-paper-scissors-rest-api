package com.example.rpsapi.api.model.entities;

public enum Move {
    ROCK {
        @Override
        public Move getWinsAgainst() {
            return SCISSORS;
        }

        @Override
        public Move getLosesAgainst() {
            return PAPER;
        }
    },
    PAPER {
        @Override
        public Move getWinsAgainst() {
            return ROCK;
        }

        @Override
        public Move getLosesAgainst() {
            return SCISSORS;
        }
    },
    SCISSORS {
        @Override
        public Move getWinsAgainst() {
            return PAPER;
        }

        @Override
        public Move getLosesAgainst() {
            return ROCK;
        }
    };

    public abstract Move getWinsAgainst();

    public abstract Move getLosesAgainst();

    public Result against(Move move) {
        if (this == move) {
            return Result.DRAW;
        } else if (move == getWinsAgainst()) { // Here's the corrected line
            return Result.WIN;
        } else if (move == getLosesAgainst()) {
            return Result.LOSE;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public enum Result {
        WIN, LOSE, DRAW
    }
}
