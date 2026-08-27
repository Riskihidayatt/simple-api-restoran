package com.project.restaurant.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response ringkas data transaksi")
public class SimpleTransactionResponse {
    @Schema(description = "ID unik transaksi", example = "txn-001-abc")
    private String id;

    @Schema(description = "Nama customer", example = "Budi Santoso")
    private String customerName;

    @Schema(description = "Tanggal dan waktu transaksi", example = "2026-08-27T12:30:00")
    private LocalDateTime transactionDate;

    @Schema(description = "Total harga transaksi", example = "75000")
    private Double totalAmount;
}
