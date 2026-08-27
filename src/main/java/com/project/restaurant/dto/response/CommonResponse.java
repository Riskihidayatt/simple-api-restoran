package com.project.restaurant.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    private String message;
    private Integer statusCode;
    private T data;
    private PaginationResponse pagination;

    // Constructor Private
    private CommonResponse(String message, Integer statusCode, T data, PaginationResponse pagination) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
        this.pagination = pagination;
    }

    // --- STATIC FACTORY METHODS UNTUK CONTROLLER ---

    public static <T> ResponseEntity<CommonResponse<T>> ok(String message, T data) {
        return ResponseEntity.ok(new CommonResponse<>(message, HttpStatus.OK.value(), data, null));
    }

    public static <T> ResponseEntity<CommonResponse<T>> ok(String message, T data, PaginationResponse pagination) {
        return ResponseEntity.ok(new CommonResponse<>(message, HttpStatus.OK.value(), data, pagination));
    }

    public static <T> ResponseEntity<CommonResponse<T>> created(String message, T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse<>(message, HttpStatus.CREATED.value(), data, null));
    }

    public static <T> ResponseEntity<CommonResponse<T>> error(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new CommonResponse<>(message, status.value(), null, null));
    }

    public static <T> ResponseEntity<CommonResponse<T>> error(String message, HttpStatus status, T data) {
        return ResponseEntity.status(status).body(new CommonResponse<>(message, status.value(), data, null));
    }
}
