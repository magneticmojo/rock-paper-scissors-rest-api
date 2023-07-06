package com.magneticmojo.rpsapi.api.exceptions;

import com.magneticmojo.rpsapi.api.exceptions.gameexception.*;
import com.magneticmojo.rpsapi.api.exceptions.playerexception.MultipleMovesProhibitedException;
import com.magneticmojo.rpsapi.api.exceptions.playerexception.PlayerNotInGameException;
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

@ControllerAdvice // TODO @TEST
public class RockPaperScissorsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGameNotFoundException(GameNotFoundException e) {
        ErrorResponse error = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({GameNotFullException.class, GameFullException.class, GameEndedException.class})
    public ResponseEntity<ErrorResponse> handleGameStateExceptions(GameException e) {
        ErrorResponse error = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PlayerNotInGameException.class)
    public ResponseEntity<ErrorResponse> handleGameException(PlayerNotInGameException e) {
        ErrorResponse error = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MultipleMovesProhibitedException.class)
    public ResponseEntity<ErrorResponse> handlePlayerException(MultipleMovesProhibitedException e) {
        ErrorResponse error = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @Override // TODO @TEST
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
}
