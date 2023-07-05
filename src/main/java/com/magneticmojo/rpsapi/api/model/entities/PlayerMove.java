package com.magneticmojo.rpsapi.api.model.entities;

import jakarta.validation.constraints.NotNull;

public record PlayerMove(@NotNull Player player, @NotNull Move move) {
}
