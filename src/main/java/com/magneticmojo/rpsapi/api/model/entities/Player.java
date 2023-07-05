package com.magneticmojo.rpsapi.api.model.entities;

import jakarta.validation.constraints.NotBlank; // TODO SPRING BOOT -> YES; RECORD SUPPORT?

public record Player(@NotBlank String name) {
}


