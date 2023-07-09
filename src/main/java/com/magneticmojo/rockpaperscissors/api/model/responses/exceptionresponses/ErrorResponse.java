package com.magneticmojo.rockpaperscissors.api.model.responses.exceptionresponses;

import jakarta.validation.constraints.NotNull;

/**
 * Represents a generic error response.
 * The response includes an error message.
 */
public record ErrorResponse(@NotNull String errorMessage) {
}
