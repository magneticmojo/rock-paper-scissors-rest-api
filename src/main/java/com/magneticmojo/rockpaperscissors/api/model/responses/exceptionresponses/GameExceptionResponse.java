package com.magneticmojo.rockpaperscissors.api.model.responses.exceptionresponses;

/**
 * Represents a generic game exception error response.
 * The class is used by the RockPaperScissorsGameExceptionHandler.
 */
public record GameExceptionResponse(String errorCode, String errorMessage) {
}
