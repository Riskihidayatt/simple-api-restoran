package com.project.restaurant.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response detail transaksi lengkap")
public class TransactionResponse {
    @Schema(description = "ID unik transaksi", example = "txn-001-abc")
    private String id;

    @Schema(description = "Data customer yang melakukan transaksi")
    private SimpleCustomerResponse customer;

    @Schema(description = "Tanggal dan waktu transaksi", example = "2026-08-27T12:30:00")
    private LocalDateTime transactionDate;

    @Schema(description = "Daftar detail item yang dipesan")
    private List<TransactionDetailResponse> details;

    @Schema(description = "Total harga transaksi", example = "75000")
    private Double totalAmount;

    @Schema(description = "Catatan tambahan", example = "Tidak pakai sambal")
    private String notes;
}
