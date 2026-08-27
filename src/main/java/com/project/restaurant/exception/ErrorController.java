package com.project.restaurant.exception;

import com.project.restaurant.dto.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CommonResponse<String>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return CommonResponse.error(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<CommonResponse<String>> handleDuplicateResourceException(DuplicateResourceException exception) {
        return CommonResponse.error(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CommonResponse<String>> handleBadRequestException(BadRequestException exception) {
        return CommonResponse.error(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return CommonResponse.error("Validation failed", HttpStatus.BAD_REQUEST, errors);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<String>> handleAllUncaughtException(Exception exception) {
        log.error("An internal server error occurred", exception);
        return CommonResponse.error(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
