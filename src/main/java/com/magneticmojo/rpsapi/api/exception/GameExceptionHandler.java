package com.magneticmojo.rpsapi.api.exception;

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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// TODO -> VIssa kan fångas av SPRING --> MÅSTE @Override
// TODO -> VIktigt att rätt HTTP statuskod skickas tillbaka

// TODO --> DUBBELKOLLA HTTP STATUS CODES

// TODO -> Dessa skulle kunna ta bort ett par av de jag skapat ******************************************************
// ProhibitedMoveException
// ProhibitedJoinException

@ControllerAdvice
public class GameExceptionHandler extends ResponseEntityExceptionHandler {

    @Override // TODO -> DOKUMENTERA ATT DENNA KUNDE GJORT MER FIXAD
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler({GameNotFoundException.class})
    public ResponseEntity<GameExceptionResponse> handleGameNotFoundException(GameNotFoundException e) {
        GameExceptionResponse error = new GameExceptionResponse("GAME NOT FOUND", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MoveProhibitedMissingPlayerException.class})
    public ResponseEntity<GameExceptionResponse> handleMoveProhibitedMissingPlayerException(MoveProhibitedMissingPlayerException e) {
        GameExceptionResponse error = new GameExceptionResponse("MOVE PROHIBITED, PLAYER MISSING", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PlayerException.class})
    public ResponseEntity<GameExceptionResponse> handleGameException(RuntimeException e) {
        GameExceptionResponse error = new GameExceptionResponse("PROHIBITED PLAYER ACTION", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({JoinFullGameException.class})
    public ResponseEntity<GameExceptionResponse> handleJoinFullGameException(JoinFullGameException e) {
        GameExceptionResponse error = new GameExceptionResponse("GAME IS FULL", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({GameEndedException.class})
    public ResponseEntity<GameExceptionResponse> handleGameEndedException(GameEndedException e) {
        GameExceptionResponse error = new GameExceptionResponse("GAME ENDED", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }



    // e.getMessage() --> Det man skrivit när man kastar undantaget

    // TODO -> ADD MORE EXCEPTIONS IF NEEDED


    // TODO -> MakeMoveException
    // TODO -> JoinGameException

    // STATE CLASSES:
    // TODO -> PlayerException (NotINGameException, PlayerAlreadyJoinedException, PlayerAlreadyMadeMoveException???)

    // CONTROLLER CLASS:
    // TODO -> ResponseStatusException??? --> Kanske inte behövs om vi har en generell exceptionhandler?
    // TODO -> For @Validated If a method parameter fails validation --> Spring throw MethodArgumentNotValidException -> 400 BAD REQUEST

    // TODO -> BAD USER INPUT --> 400 BAD REQUEST
    // TODO -> BAD SERVER STATE --> 500 INTERNAL SERVER ERROR

    // Serializers:
    // TODO -> HOW TO HANDLE IOExceptions?
    // TODO --> PROGRAMMMING ERROR --> 500 INTERNAL SERVER ERROR

    // TODO -> Provide generic MESSAGES TO CLIENTS

    // Types of needed?
    // KOLLA PÅ LISTAN SOM GES AV SPRING

    // TODO -> USE @ResponseStatus annotation to set the status code of HTTP response
}
