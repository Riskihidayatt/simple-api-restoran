package com.enigma.restaurant.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionItemRequest {
    @NotBlank(message = "Menu ID tidak boleh kosong")
    private String menuId;

    @NotNull(message = "Quantity tidak boleh kosong")
    @Min(value = 1, message = "Quantity minimal 1")
    private Integer quantity;
}