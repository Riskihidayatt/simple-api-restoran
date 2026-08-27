package com.project.restaurant.dto.request;

import com.project.restaurant.enums.MenuCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
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
@Schema(description = "Request body to update a menu item")
public class UpdateMenuRequest {
    @Size(max = 100, message = "Menu name must not exceed 100 characters")
    @Schema(description = "New menu name (optional)", example = "Nasi Goreng Premium")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Schema(description = "New description (optional)", example = "Nasi goreng premium dengan seafood")
    private String description;

    @Min(value = 1000, message = "Price must be at least 1000")
    @Schema(description = "New price (optional)", example = "35000")
    private Double price;

    @Schema(description = "Menu availability status (optional)", example = "true")
    private Boolean isAvailable;

    @Schema(description = "New category (optional)", example = "FOOD")
    private MenuCategory category;
}
