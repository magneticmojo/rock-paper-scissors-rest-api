package com.magneticmojo.rockpaperscissors.api.exceptionhandler;

import com.magneticmojo.rockpaperscissors.api.model.responses.ErrorResponse;
import com.magneticmojo.rockpaperscissors.api.model.responses.ValidationErrorResponse;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.exceptions.GameNotFoundException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/** // TODO CHange
 * RockPaperScissorsAPIExceptionHandler is a central place for handling exceptions across the whole application.
 * It is annotated with @ControllerAdvice to be able to assist multiple controllers.
 * <p>
 * This handler deals with various exceptions such as GameNotFoundException, MissingPlayerTwoException,
 * GameFullException, GameEndedException, PlayerNotInGameException, and MultipleMovesProhibitedException
 * by wrapping them in a ResponseEntity with a suitable HTTP status code and a custom ErrorResponse.
 * <p>
 * Additionally, it handles validation errors by overriding the handleMethodArgumentNotValid method
 * from ResponseEntityExceptionHandler. If there are validation errors, it generates a list of error messages
 * and wraps them in a ValidationErrorResponse.
 */

@ControllerAdvice
public class RockPaperScissorsAPIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGameNotFoundException(GameNotFoundException e) {
        return createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MissingPlayerTwoException.class, GameFullException.class, GameEndedException.class})
    public ResponseEntity<ErrorResponse> handleGameStateExceptions(RuntimeException e) {
        return createErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PlayerNotInGameException.class)
    public ResponseEntity<ErrorResponse> handleGameException(PlayerNotInGameException e) {
        return createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MultipleMovesProhibitedException.class)
    public ResponseEntity<ErrorResponse> handlePlayerException(MultipleMovesProhibitedException e) {
        return createErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ValidationErrorResponse response = new ValidationErrorResponse(status.value(), errors);
        return new ResponseEntity<>(response, headers, status);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(String message, HttpStatus httpStatus) {
        ErrorResponse error = new ErrorResponse(message);
        return new ResponseEntity<>(error, httpStatus);
    }
}

