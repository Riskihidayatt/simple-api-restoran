package com.enigma.restaurant.exception;

import com.enigma.restaurant.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CommonResponse<String>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        CommonResponse<String> response = new CommonResponse<>(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<CommonResponse<String>> handleDuplicateResourceException(DuplicateResourceException exception) {
        CommonResponse<String> response = new CommonResponse<>(
                exception.getMessage(),
                HttpStatus.CONFLICT.value()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CommonResponse<String>> handleBadRequestException(BadRequestException exception) {
        CommonResponse<String> response = new CommonResponse<>(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        CommonResponse<Map<String, String>> response = new CommonResponse<>(
                "Validasi gagal",
                HttpStatus.BAD_REQUEST.value(),
                errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<String>> handleAllUncaughtException(Exception exception) {
        CommonResponse<String> response = new CommonResponse<>(
                "Terjadi kesalahan internal pada server: " + exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}