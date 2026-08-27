package com.project.restaurant.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Informasi pagination")
public class PaginationResponse {
    @Schema(description = "Halaman saat ini (dimulai dari 0)", example = "0")
    private Integer currentPage;

    @Schema(description = "Total jumlah data keseluruhan", example = "25")
    private Long totalElements;

    @Schema(description = "Total jumlah halaman", example = "3")
    private Integer totalPages;

    @Schema(description = "Jumlah data per halaman", example = "10")
    private Integer pageSize;

    public PaginationResponse(Page<?> page) {
        this.currentPage = page.getNumber();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.pageSize = page.getSize();
    }
}
