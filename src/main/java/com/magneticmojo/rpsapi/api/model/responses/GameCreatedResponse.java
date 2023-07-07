package com.magneticmojo.rpsapi.api.model.responses;

import jakarta.validation.constraints.NotNull;

/**
 * Represents a response for a newly created game.
 * The response includes a message and an ID.
 */
public record GameCreatedResponse(@NotNull String message, @NotNull String id) {
}
