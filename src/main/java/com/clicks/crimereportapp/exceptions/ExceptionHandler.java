package com.clicks.crimereportapp.exceptions;

import com.clicks.crimereportapp.utils.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomResponse> handleNotFound(RuntimeException exception) {
        return ResponseEntity.status(NOT_FOUND).body(new CustomResponse(exception.getMessage(), emptyMap()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidParamsException.class)
    public ResponseEntity<CustomResponse> handleInvalidParams(RuntimeException exception) {
        return ResponseEntity.status(BAD_REQUEST).body(new CustomResponse(exception.getMessage(), emptyMap()));
    }
}
