package com.magneticmojo.rockpaperscissors.api.exceptionhandlers;

import com.magneticmojo.rockpaperscissors.api.controller.RockPaperScissorsGameController;
import com.magneticmojo.rockpaperscissors.api.model.responses.exceptionresponses.GameExceptionResponse;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.exceptions.GameNotFoundException;
import com.magneticmojo.rockpaperscissors.services.rockpaperscissors.game.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * RockPaperScissorsGameExceptionHandler is a central place for handling exceptions across the whole application.
 * It is annotated with @ControllerAdvice to be able to assist multiple controllers.
 * <p>
 * This handler deals with various RockPaperScissorsGameException exceptions such as GameNotFoundException, MissingPlayerTwoException,
 * GameFullException, GameEndedException, PlayerNotInGameException, and MultipleMovesProhibitedException
 * by wrapping them in a ResponseEntity with a suitable HTTP status code and a custom GameExceptionResponse.
 * <p>
 * ValidationExceptions are handled by the RequestValidationExceptionHandler.
 */
@ControllerAdvice(assignableTypes = {RockPaperScissorsGameController.class})
public class RockPaperScissorsGameExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<GameExceptionResponse> handleGameNotFoundException(GameNotFoundException e) {
        return createErrorResponse(e.getErrorCode(), e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlayerNotInGameException.class)
    public ResponseEntity<GameExceptionResponse> handlePlayerNotInGameException(PlayerNotInGameException e) {
        return createErrorResponse(e.getErrorCode(), e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            MissingPlayerTwoException.class,
            MultipleMovesProhibitedException.class,
            GameFullException.class,
            GameEndedException.class})
    public ResponseEntity<GameExceptionResponse> handleGameStateExceptions(RockPaperScissorsGameException e) {
        return createErrorResponse(e.getErrorCode(), e.getMessage(), HttpStatus.CONFLICT);
    }

    private ResponseEntity<GameExceptionResponse> createErrorResponse(String errorCode, String message, HttpStatus httpStatus) {
        GameExceptionResponse error = new GameExceptionResponse(errorCode, message);
        return new ResponseEntity<>(error, httpStatus);
    }
}

