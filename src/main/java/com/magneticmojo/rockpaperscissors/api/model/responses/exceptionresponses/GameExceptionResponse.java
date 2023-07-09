package com.magneticmojo.rockpaperscissors.api.model.responses.exceptionresponses;

import jakarta.validation.constraints.NotNull;

/**
 * Represents a generic error response.
 * The response includes an error message.
 */
public record GameExceptionResponse(@NotNull String errorCode, @NotNull String errorMessage) {
}
