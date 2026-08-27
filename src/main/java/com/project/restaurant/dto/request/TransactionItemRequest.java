package com.project.restaurant.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionItemRequest {
    @NotBlank(message = "Menu ID tidak boleh kosong")
    private String menuId;

    @NotNull(message = "Quantity tidak boleh kosong")
    @Min(value = 1, message = "Quantity minimal 1")
    private Integer quantity;
}