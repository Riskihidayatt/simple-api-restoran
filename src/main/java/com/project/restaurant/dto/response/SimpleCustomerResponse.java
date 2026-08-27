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
@Schema(description = "Response ringkas data customer")
public class SimpleCustomerResponse {
    @Schema(description = "ID unik customer", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @Schema(description = "Nama customer", example = "Budi Santoso")
    private String name;

    @Schema(description = "Email customer", example = "budi@email.com")
    private String email;

    @Schema(description = "Nomor telepon", example = "081234567890")
    private String phone;
}
