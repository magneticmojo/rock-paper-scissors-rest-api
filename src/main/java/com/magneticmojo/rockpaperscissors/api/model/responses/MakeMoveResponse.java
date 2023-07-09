package com.magneticmojo.rockpaperscissors.api.model.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rockpaperscissors.api.serialization.responses.MakeMoveResponseSerializer;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;

@JsonSerialize(using = MakeMoveResponseSerializer.class)
public record MakeMoveResponse(PlayerMove playerMove, String movePosition, String gameId) {
}
