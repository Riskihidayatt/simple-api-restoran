package com.project.restaurant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body to create a new transaction")
public class CreateTransactionRequest {
    @NotBlank(message = "Customer ID must not be empty")
    @Schema(description = "ID of the customer making the transaction", example = "550e8400-e29b-41d4-a716-446655440000")
    private String customerId;

    @NotEmpty(message = "At least 1 menu item is required")
    @Valid
    @Schema(description = "List of menu items ordered")
    private List<TransactionItemRequest> items;

    @Schema(description = "Additional notes (optional)", example = "Tidak pakai sambal")
    private String notes;
}

