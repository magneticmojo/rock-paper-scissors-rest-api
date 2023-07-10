package com.magneticmojo.rockpaperscissors.api.exceptionhandlers;

import com.magneticmojo.rockpaperscissors.api.model.responses.exceptionresponses.RequestValidationErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestValidationExceptionHandlerTest {

    private RequestValidationExceptionHandler exceptionHandler;

    @BeforeEach
    public void setUp() {
        exceptionHandler = new RequestValidationExceptionHandler();
    }

    @Test
    public void testHandleMethodArgumentNotValid() {
        // Create a mock MethodArgumentNotValidException
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");

        // Setup the mock to return a list of FieldErrors when getFieldErrors is called
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        // Call the method under test
        HttpHeaders httpHeaders = new HttpHeaders();  // Instantiate HttpHeaders
        ResponseEntity<Object> responseEntity = exceptionHandler.handleMethodArgumentNotValid(
                ex, httpHeaders, HttpStatus.BAD_REQUEST, mock(WebRequest.class));  // Use actual HttpHeaders instance

        // Validate the response
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof RequestValidationErrorResponse);

        RequestValidationErrorResponse validationErrorResponse = (RequestValidationErrorResponse) responseEntity.getBody();
        assertEquals(1, validationErrorResponse.errors().size());
        assertEquals("defaultMessage", validationErrorResponse.errors().get(0));
    }

}
