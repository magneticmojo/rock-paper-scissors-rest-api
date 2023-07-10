package com.magneticmojo.rockpaperscissors.api.model.responses.exceptionresponses;

/**
 * Represents a generic game exception error response.
 */
public record GameExceptionResponse(String errorCode, String errorMessage) {
}
