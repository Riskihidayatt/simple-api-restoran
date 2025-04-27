package com.enigma.restaurant.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    private String message;
    private Integer statusCode;
    private T data;
    private PaginationResponse pagination;

    // Constructor tanpa pagination (untuk create, getById, update, delete, error)
    public CommonResponse(String message, Integer statusCode, T data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    // Constructor dengan pagination (untuk getAll)
    public CommonResponse(String message, Integer statusCode, T data, PaginationResponse pagination) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
        this.pagination = pagination;
    }

    // Constructor untuk response tanpa data body (misal delete sukses, error tanpa detail)
    public CommonResponse(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
