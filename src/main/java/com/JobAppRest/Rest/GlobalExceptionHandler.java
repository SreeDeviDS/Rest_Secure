package com.JobAppRest.Rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JobPostNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleJobPostNotFound(JobPostNotFoundException ex) {
        return new ResponseEntity<>(body(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String message = ex.getFieldError() != null ? ex.getFieldError().getDefaultMessage() : "Validation failed";
        return new ResponseEntity<>(body(HttpStatus.BAD_REQUEST, message), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> body(HttpStatus status, String message) {
        return Map.of(
                "timestamp", Instant.now().toString(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message
        );
    }

}

