package org.parkTub.customer.exception;

import org.parkTub.customer.exception.custom.CustomerNotFoundException;
import org.parkTub.customer.response.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handle(CustomerNotFoundException exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ApiResponse<String> apiResponse = new ApiResponse<>(
                errorMessage, null);

        return ResponseEntity.status(BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handle(Exception exp) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                exp.getMessage(), null);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(apiResponse);
    }
}
