package com.magneticmojo.rockpaperscissors.api.model.responses.gameresponses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rockpaperscissors.api.serialization.responses.CreateGameResponseSerializer;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonSerialize(using = CreateGameResponseSerializer.class)
public record CreateGameResponse(@NotBlank String id, @NotNull Player player) {
}
