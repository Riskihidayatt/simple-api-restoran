package com.enigma.restaurant.dto.response;
import com.enigma.restaurant.dto.response.SimpleMenuResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionDetailResponse {
    private SimpleMenuResponse menu; // Nested DTO
    private Integer quantity;
    private Double price;
    private Double subtotal;
}
