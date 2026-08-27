package com.project.restaurant.dto.response;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationResponse {
    private Integer currentPage;
    private Long totalElements;
    private Integer totalPages;
    private Integer pageSize;

    // Constructor otomatis ekstrak dari Spring Page
    public PaginationResponse(Page<?> page) {
        this.currentPage = page.getNumber();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.pageSize = page.getSize();
    }
}