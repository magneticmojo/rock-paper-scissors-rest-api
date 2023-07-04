package com.example.rpsapi.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// TODO -> VIssa kan fångas av SPRING --> MÅSTE @Override
// TODO -> VIktigt att rätt HTTP statuskod skickas tillbaka
@ControllerAdvice
public class GameExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler( {IllegalArgumentException.class, PlayerException.class} )
    public ResponseEntity<String> handleGameException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( {GameNotFoundException.class} )
    public ResponseEntity<String> handleNoGameFoundException(GameNotFoundException e) {

        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    // TODO -> ADD MORE EXCEPTIONS IF NEEDED

}
