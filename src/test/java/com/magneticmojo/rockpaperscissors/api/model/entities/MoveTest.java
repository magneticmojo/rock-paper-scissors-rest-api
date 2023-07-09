package com.magneticmojo.rockpaperscissors.api.model.entities;

import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Move;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Move class.
 * <p>
 * These tests validate the behavior of the Move class, specifically
 * checking the rules of the Rock-Paper-Scissors game:
 * - Rock beats Scissors
 * - Paper beats Rock
 * - Scissors beats Paper
 * <p>
 * The tests also include checks for moves that result in a tie.
 * <p>
 * Additionally, the tests ensure that the enum values of Move
 * are in the correct order: ROCK, PAPER, SCISSORS.
 */
public class MoveTest {

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

    @Test
    void correctEnumOrder() {
        assertEquals(0, Move.ROCK.ordinal());
        assertEquals(1, Move.PAPER.ordinal());
        assertEquals(2, Move.SCISSORS.ordinal());
    }
}