package com.magneticmojo.rpsapi.api.exceptions;

import java.util.List;

public record ValidationErrorResponse(int HttpStatusCode, List<String> errors) {
}
