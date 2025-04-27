package com.enigma.restaurant.dto.response;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SimpleTransactionResponse {
    private String id;
    private String customerName;
    private LocalDateTime transactionDate;
    private Double totalAmount;
}
