package com.magneticmojo.rpsapi.api.exceptions;

import jakarta.validation.constraints.NotNull;

public record ErrorResponse(@NotNull String errorMessage) {
}
