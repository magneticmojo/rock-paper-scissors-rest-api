package com.magneticmojo.rpsapi.api.state;

import com.magneticmojo.rpsapi.api.model.entities.Player;
import com.magneticmojo.rpsapi.api.model.entities.PlayerMove;
// TODO --> RENAME CONCRETE CLASSES
public interface GameState { // TODO @TEST???

    GameState joinGame(Player player);
    GameState makeMove(PlayerMove playerMove);

}
