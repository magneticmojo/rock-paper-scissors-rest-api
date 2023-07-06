package com.magneticmojo.rpsapi.api.model.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @Test
    void testRockBeatsScissors() {
        Move rock = Move.ROCK;
        Move scissors = Move.SCISSORS;
        assertTrue(rock.isWinnerAgainst(scissors));
    }

    @Test
    void testPaperBeatsRock() {
        Move paper = Move.PAPER;
        Move rock = Move.ROCK;
        assertTrue(paper.isWinnerAgainst(rock));
    }

    @Test
    void testScissorsBeatsPaper() {
        Move scissors = Move.SCISSORS;
        Move paper = Move.PAPER;
        assertTrue(scissors.isWinnerAgainst(paper));
    }

    @Test
    void isTied() {
        assertTrue(Move.ROCK.isTied(Move.ROCK));
        assertTrue(Move.PAPER.isTied(Move.PAPER));
        assertTrue(Move.SCISSORS.isTied(Move.SCISSORS));
    }

}