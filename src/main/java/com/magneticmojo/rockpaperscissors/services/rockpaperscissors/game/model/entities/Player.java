package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities;

import jakarta.validation.constraints.NotBlank;

/**
 * Represents a player entity.
 * The player is identified by their name.
 */
public record Player(@NotBlank String name) {
}


