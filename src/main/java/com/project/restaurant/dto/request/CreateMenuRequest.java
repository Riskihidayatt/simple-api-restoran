package com.project.restaurant.dto.request;
import com.project.restaurant.enums.MenuCategory;
import jakarta.validation.constraints.*;
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
public class CreateMenuRequest {
    @NotBlank(message = "Nama menu tidak boleh kosong")
    @Size(max = 100, message = "Nama menu maksimal 100 karakter")
    private String name;

    @Size(max = 500, message = "Deskripsi maksimal 500 karakter")
    private String description;

    @NotNull(message = "Harga tidak boleh kosong")
    @Min(value = 1000, message = "Harga minimal Rp 1.000")
    private Double price;

    @NotNull(message = "Kategori tidak boleh kosong")
    private MenuCategory category;

}
