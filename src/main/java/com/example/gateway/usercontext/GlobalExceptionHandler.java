package com.example.gateway.usercontext;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, RuntimeException.class})
    public ResponseEntity<String> handleAuthExceptions(Exception e) {
        UserContext.clear();
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}