package com.project.restaurant.dto.response;
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
public class TransactionDetailResponse {
    private SimpleMenuResponse menu; // Nested DTO
    private Integer quantity;
    private Double price;
    private Double subtotal;
}
