package com.magneticmojo.rockpaperscissors.api.model.responses.exceptionresponses;

import java.util.List;

/**
 * Represents a response for validation errors. The class is used by the RequestValidationExceptionHandler.
 * The response includes the HTTP status code and a list of error messages.
 */
public record RequestValidationErrorResponse(int HttpStatusCode, List<String> errors) {
}
