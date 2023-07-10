package com.magneticmojo.rockpaperscissors.api.exceptionhandlers;

import com.magneticmojo.rockpaperscissors.api.controller.RockPaperScissorsGameController;
import com.magneticmojo.rockpaperscissors.api.model.responses.exceptionresponses.RequestValidationErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This is a global exception handler class that is responsible for handling
 * validation errors thrown by the application's RESTful API controller.
 * It specifically handles `MethodArgumentNotValidException` exceptions,
 * occurring when arguments annotated with validation constraints
 * (Jakarta Bean Validation annotations) in controller methods violate
 * those constraints.
 *
 * The handler method captures these validation errors, formats them
 * into a structured `RequestValidationErrorResponse` and returns it in
 * the HTTP response, ensuring that API clients receive comprehensible
 * and uniform error messages.
 *
 * The class extends `ResponseEntityExceptionHandler` to inherit its
 * methods for handling standard Spring MVC exceptions.
 *
 * <p> This handler is associated with the `RockPaperScissorsGameController` class
 * but can be expanded to handle exceptions across multiple controllers.
 */

@ControllerAdvice(assignableTypes = {RockPaperScissorsGameController.class})
public class RequestValidationExceptionHandler extends ResponseEntityExceptionHandler {

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

        RequestValidationErrorResponse response = new RequestValidationErrorResponse(status.value(), errors);
        return new ResponseEntity<>(response, headers, status);
    }
}
