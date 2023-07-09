package com.magneticmojo.rockpaperscissors.api.model.requests;

import jakarta.validation.constraints.NotBlank;

public record PlayerRequest(@NotBlank String name) {
}
