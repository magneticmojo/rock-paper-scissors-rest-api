package com.magneticmojo.rpsapi.api.model.entities;

import jakarta.validation.constraints.NotBlank;

public record Player(@NotBlank String name) {
}


