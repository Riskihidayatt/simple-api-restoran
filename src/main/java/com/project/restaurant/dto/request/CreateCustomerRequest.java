package com.project.restaurant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "Request body to create a new customer")
public class CreateCustomerRequest {
    @NotBlank(message = "Customer name must not be empty")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Schema(description = "Customer's full name", example = "Budi Santoso")
    private String name;

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Schema(description = "Customer's email address (optional)", example = "budi@email.com")
    private String email;

    @NotBlank(message = "Phone number must not be empty")
    @Size(min = 10, max = 20, message = "Phone number must be between 10 and 20 digits")
    @Schema(description = "Customer's phone number", example = "081234567890")
    private String phone;

    @Schema(description = "Customer's address (optional)", example = "Jl. Merdeka No. 10, Jakarta")
    private String address;
}
