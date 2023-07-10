package com.magneticmojo.rockpaperscissors.api.model.responses.exceptionresponses;

/**
 * Represents a generic error response.
 * The response includes an error message.
 */
public record GameExceptionResponse(String errorCode, String errorMessage) {
}
