package com.enigma.restaurant.dto.request;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateCustomerRequest {
    @NotBlank(message = "Nama customer tidak boleh kosong")
    @Size(max = 100, message = "Nama maksimal 100 karakter")
    private String name;

    @Email(message = "Format email tidak valid")
    @Size(max = 100, message = "Email maksimal 100 karakter")
    private String email; // Bisa null jika tidak wajib

    @NotBlank(message = "Nomor telepon tidak boleh kosong")
    @Size(min = 10, max = 20, message = "Nomor telepon harus antara 10 dan 20 digit")
    private String phone;

    private String address;
}
