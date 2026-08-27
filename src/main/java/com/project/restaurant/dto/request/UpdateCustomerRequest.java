package com.project.restaurant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
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
@Schema(description = "Request body to update customer data")
public class UpdateCustomerRequest {
    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Schema(description = "Customer's new name (optional)", example = "Budi Prasetyo")
    private String name;

    @Size(min = 10, max = 20, message = "Phone number must be between 10 and 20 digits")
    @Schema(description = "New phone number (optional)", example = "089876543210")
    private String phone;

    @Schema(description = "New address (optional)", example = "Jl. Sudirman No. 5, Bandung")
    private String address;
}
