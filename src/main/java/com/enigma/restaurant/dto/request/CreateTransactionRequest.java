package com.enigma.restaurant.dto.request;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CreateTransactionRequest {
    @NotBlank(message = "Customer ID tidak boleh kosong")
    private String customerId;

    @NotEmpty(message = "Minimal harus ada 1 item menu")
    @Valid // Penting untuk validasi nested object
    private List<TransactionItemRequest> items;

    private String notes;
}