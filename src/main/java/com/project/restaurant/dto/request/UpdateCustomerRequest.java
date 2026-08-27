package com.project.restaurant.dto.request;

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
public class UpdateCustomerRequest {
    // Email biasanya tidak diupdate via request biasa
    @Size(max = 100, message = "Nama maksimal 100 karakter")
    private String name; // Opsional

    @Size(min = 10, max = 20, message = "Nomor telepon harus antara 10 dan 20 digit")
    private String phone; // Opsional

    private String address; // Opsional
}