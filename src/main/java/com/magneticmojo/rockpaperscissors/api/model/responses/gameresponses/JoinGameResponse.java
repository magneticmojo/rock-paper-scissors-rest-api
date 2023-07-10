package com.magneticmojo.rockpaperscissors.api.model.responses.gameresponses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.magneticmojo.rockpaperscissors.api.serialization.responses.JoinGameResponseSerializer;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities.Player;
import jakarta.validation.constraints.NotNull;

/**
 * Used by RockPaperScissorsController to serialize the response to a join game request.
 */
@JsonSerialize(using = JoinGameResponseSerializer.class)
public record JoinGameResponse(@NotNull Player player) {
}
