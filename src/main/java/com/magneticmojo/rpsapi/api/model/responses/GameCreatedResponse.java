package com.magneticmojo.rpsapi.api.model.responses;

import jakarta.validation.constraints.NotNull;

public record GameCreatedResponse(@NotNull String msg, @NotNull String id) {
}