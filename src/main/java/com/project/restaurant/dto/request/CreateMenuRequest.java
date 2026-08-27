package com.project.restaurant.dto.request;

import com.project.restaurant.enums.MenuCategory;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Request body to create a new menu item")
public class CreateMenuRequest {
    @NotBlank(message = "Menu name must not be empty")
    @Size(max = 100, message = "Menu name must not exceed 100 characters")
    @Schema(description = "Menu name", example = "Nasi Goreng Spesial")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Schema(description = "Menu description (optional)", example = "Nasi goreng dengan telur, ayam, dan sayuran")
    private String description;

    @NotNull(message = "Price must not be empty")
    @Min(value = 1000, message = "Price must be at least 1000")
    @Schema(description = "Menu price in Rupiah", example = "25000")
    private Double price;

    @NotNull(message = "Category must not be empty")
    @Schema(description = "Menu category", example = "FOOD")
    private MenuCategory category;
}
