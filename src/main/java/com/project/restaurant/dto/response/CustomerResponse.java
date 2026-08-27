package com.project.restaurant.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response detail customer lengkap")
public class CustomerResponse {
    @Schema(description = "ID unik customer", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @Schema(description = "Nama customer", example = "Budi Santoso")
    private String name;

    @Schema(description = "Email customer", example = "budi@email.com")
    private String email;

    @Schema(description = "Nomor telepon", example = "081234567890")
    private String phone;

    @Schema(description = "Alamat customer", example = "Jl. Merdeka No. 10, Jakarta")
    private String address;

    @Schema(description = "Tanggal pembuatan data", example = "2026-08-27T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Tanggal terakhir diupdate", example = "2026-08-27T14:00:00")
    private LocalDateTime updatedAt;
}
