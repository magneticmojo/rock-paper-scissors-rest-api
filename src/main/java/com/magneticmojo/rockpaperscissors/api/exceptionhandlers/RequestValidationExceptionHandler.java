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
