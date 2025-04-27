package com.enigma.restaurant.dto.request;
import com.enigma.restaurant.enums.MenuCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateMenuRequest {
    @Size(max = 100, message = "Nama menu maksimal 100 karakter")
    private String name;

    @Size(max = 500, message = "Deskripsi maksimal 500 karakter")
    private String description;

    @Min(value = 1000, message = "Harga minimal Rp 1.000")
    private Double price;

    private Boolean isAvailable;

    private MenuCategory category;

}