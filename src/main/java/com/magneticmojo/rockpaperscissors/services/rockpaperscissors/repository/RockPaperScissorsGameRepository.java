package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.repository;

import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.RockPaperScissorsGame;

public interface RockPaperScissorsGameRepository {

    void addGame(RockPaperScissorsGame game);

    RockPaperScissorsGame getGame(String id);
}
