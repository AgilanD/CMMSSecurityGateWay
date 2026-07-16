package com.example.gateway.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(feign.FeignException.class)
    public ResponseEntity<APIResponse<Object>> handleFeignException(feign.FeignException ex, WebRequest request) {
        int status = ex.status() >= 100 ? ex.status() : 500;
        String content = ex.contentUTF8();
        String message = ex.getMessage();

        if (content != null && content.contains("\"message\":\"")) {
            String temp = content.substring(content.indexOf("\"message\":\"") + 11);
            message = temp.substring(0, temp.indexOf("\""));
        }

        HttpStatus httpStatus = HttpStatus.resolve(status);
        String errorName = (httpStatus != null) ? httpStatus.getReasonPhrase() : "Feign Error";

        APIResponse<Object> response = new APIResponse<>(
                status,
                errorName,
                message,
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, httpStatus != null ? httpStatus : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({IllegalArgumentException.class, RuntimeException.class})
    public ResponseEntity<APIResponse<Object>> handleAuthAndRuntimeExceptions(Exception ex, WebRequest request) {
        com.example.gateway.usercontext.UserContext.clear();
        APIResponse<Object> response = new APIResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<APIResponse<Object>> handleCustomerNotFoundException(CustomerNotFoundException ex, WebRequest request) {
        APIResponse<Object> response = new APIResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<APIResponse<Object>> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        HttpStatus status = HttpStatus.resolve(ex.getStatusCode().value());
        String errorName = (status != null) ? status.getReasonPhrase() : "Error";

        APIResponse<Object> response = new APIResponse<>(
                ex.getStatusCode().value(),
                errorName,
                ex.getReason(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, ex.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Object>> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        APIResponse<Object> response = new APIResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                errors,
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<APIResponse<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String paramName = java.util.Optional.ofNullable(ex.getName()).orElse("unknown");

        String typeName = java.util.Optional.ofNullable(ex.getRequiredType())
                .map(Class::getSimpleName)
                .orElse("Unknown");

        String msg = String.format("Parameter '%s' must be of type %s", paramName, typeName);

        APIResponse<Object> response = new APIResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Type Mismatch",
                msg,
                java.util.Optional.ofNullable(request.getDescription(false)).orElse(""),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(java.util.NoSuchElementException.class)
    public ResponseEntity<APIResponse<Object>> handleNoSuchElementException(java.util.NoSuchElementException ex, WebRequest request) {
        APIResponse<Object> response = new APIResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",
                "The requested record does not exist in the system.",
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<APIResponse<Object>> handleNullPointerException(NullPointerException ex, WebRequest request) {
        APIResponse<Object> response = new APIResponse<>(
                HttpStatus.NOT_FOUND.value(),
                "Data Error",
                "The requested resource was not found or contains empty data.",
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Object>> handleGenericException(Exception ex, WebRequest request) {
        APIResponse<Object> response = new APIResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
