package com.project.restaurant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Item details in a transaction")
public class TransactionItemRequest {
    @NotBlank(message = "Menu ID must not be empty")
    @Schema(description = "Ordered menu ID", example = "506fed07-a55a-475d-aba4-6ff00063f7e4")
    private String menuId;

    @NotNull(message = "Quantity must not be empty")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Schema(description = "Number of items ordered", example = "2")
    private Integer quantity;
}

