package com.enigma.restaurant.dto.response;

import lombok.*;

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
}