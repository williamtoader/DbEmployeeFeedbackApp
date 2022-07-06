package com.db.cloudschool.employeefeedback.advice;

import com.db.cloudschool.employeefeedback.payload.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(
            Exception ex, WebRequest request
    ) {
        return new ResponseEntity<Object>(new ErrorResponse("Authentication failure", ex.toString()), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler({HttpClientErrorException.Unauthorized.class})
    public ResponseEntity<Object> handleUnauthorizedException(
            Exception ex, WebRequest request
    ) {
        return new ResponseEntity<Object>(new ErrorResponse("Unauthorized"), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler({HttpClientErrorException.Forbidden.class})
    public ResponseEntity<Object> handleForbiddenException(
            Exception ex, WebRequest request
    ) {
        return new ResponseEntity<Object>(new ErrorResponse("Forbidden"), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(
            Exception ex, WebRequest request
    ) {
        return new ResponseEntity<Object>(new ErrorResponse("Runtime exception", ex.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
