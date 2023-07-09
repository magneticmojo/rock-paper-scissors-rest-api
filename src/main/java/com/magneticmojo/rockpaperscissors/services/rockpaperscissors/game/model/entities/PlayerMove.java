package com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.model.entities;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
/**
 * A record that represents a move made by a player in the game.
 * It includes a player entity and the move made.
 *
 * <p>The {@code @Validated} annotation on the Player field is used to activate
 * nested validation.</p>
 *
 * @param player The Player making the move.
 * @param move The move made by the player.
 */
public record PlayerMove(@Valid @NotNull Player player, @Valid @NotNull Move move) {
}
