package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.repository;

import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.RockPaperScissorsGame;

/**
 * Defines the contract for game repositories. Supports basic operations to add and retrieve games.
 */
public interface RockPaperScissorsGameRepository {

    void addGame(RockPaperScissorsGame game);

    RockPaperScissorsGame getGame(String id);
}
