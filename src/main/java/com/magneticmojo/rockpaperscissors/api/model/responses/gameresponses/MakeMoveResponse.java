package com.magneticmojo.rockpaperscissors.api.model.responses.gameresponses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rockpaperscissors.api.serialization.responses.MakeMoveResponseSerializer;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.PlayerMove;
import jakarta.validation.constraints.NotNull;

@JsonSerialize(using = MakeMoveResponseSerializer.class)
public record MakeMoveResponse(@NotNull PlayerMove playerMove, @NotNull String movePosition) {
}
