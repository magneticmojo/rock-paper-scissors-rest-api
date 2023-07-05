package com.example.rpsapi.api.state;

import com.example.rpsapi.api.model.entities.Move;
import com.example.rpsapi.api.model.entities.Player;
import com.example.rpsapi.api.model.entities.PlayerMove;

public interface GameState { // TODO -> REMOVE RETURN TYPE FROM METHODS?

    GameState joinGame(Player player);
    GameState makeMove(PlayerMove playerMove);
}

/*
Request: POST /api/games
Server operations:
Create playerOne from JSON request body input "name".
Create an instance of RockPaperScissorsGame which gets assigned a UUID in the constructor.
Set game state to GameCreatedState.
Return the "id" for the game (corresponding to the generated UUID)

State: GameCreatedState
Transition: No transition (because game was just created)

GET /api/games/{id}
Server operations:
Fetch RockPaperScissorsGame instance based on the JSON request body input "id".
Return game state with information about the game instance.

State: GameCreatedState (if no other request than POST has been made)

PATCH /api/games/{id}/join
Server operations:
Create and adds player two, with name from JSON request body "name", to game with gameID corresponding to "id" in path.

State: GameReadyState or PlayerTwoJoinedState (or other possibly better suggestions from you)
Transition: GameCreatedState (previous state) --> GameReadyState (current state)

PATCH /api/games/{id}/move
Server operation:
Either player makes their single move, "id" from path and "name" and "move" in JSON request body. The move property of the player making the request is updated.

State: GameActiveState
Transition: GameReadyState (previous state) --> GameActiveState (current state)

(Call #2)
PATCH /api/games/{id}/move
Server operation:
The other player makes its single move, "id" from path and "name" and "move" in JSON request body. The move property of the player making the request is updated.

State: GameFinishedState
Transition: GameActiveState (previous state) --> GameFinishedState (current state)
* */
