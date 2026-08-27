package com.project.restaurant.dto.response;

import com.project.restaurant.enums.MenuCategory;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Response detail menu")
public class MenuResponse {
    @Schema(description = "ID unik menu", example = "menu-001-xyz")
    private String id;

    @Schema(description = "Nama menu", example = "Nasi Goreng Spesial")
    private String name;

    @Schema(description = "Deskripsi menu", example = "Nasi goreng dengan telur, ayam, dan sayuran")
    private String description;

    @Schema(description = "Harga dalam Rupiah", example = "25000")
    private Double price;

    @Schema(description = "Kategori menu", example = "FOOD")
    private MenuCategory category;

    @Schema(description = "Status ketersediaan", example = "true")
    private Boolean isAvailable;
}
