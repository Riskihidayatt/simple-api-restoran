package com.project.restaurant.dto.response;

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
@Schema(description = "Detail item dalam transaksi")
public class TransactionDetailResponse {
    @Schema(description = "Data menu yang dipesan")
    private SimpleMenuResponse menu;

    @Schema(description = "Jumlah item", example = "2")
    private Integer quantity;

    @Schema(description = "Harga satuan saat transaksi", example = "25000")
    private Double price;

    @Schema(description = "Subtotal (harga x quantity)", example = "50000")
    private Double subtotal;
}
