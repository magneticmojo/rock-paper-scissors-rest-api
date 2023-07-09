package com.magneticmojo.rockpaperscissors.api.model.responses.gameresponses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rockpaperscissors.api.serialization.responses.CreateGameResponseSerializer;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a response for a newly created game.
 */
@JsonSerialize(using = CreateGameResponseSerializer.class)
public record CreateGameResponse(@NotNull String id) {
}
