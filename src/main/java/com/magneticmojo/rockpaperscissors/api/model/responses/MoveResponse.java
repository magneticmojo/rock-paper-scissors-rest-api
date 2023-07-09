package com.magneticmojo.rockpaperscissors.api.model.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rockpaperscissors.api.serialization.MoveResponseSerializer;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;

@JsonSerialize(using = MoveResponseSerializer.class)
public record MoveResponse(PlayerMove playerMove, String movePosition) {
}
