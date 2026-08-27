package com.project.restaurant.dto.response;
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
public class SimpleTransactionResponse {
    private String id;
    private String customerName;
    private LocalDateTime transactionDate;
    private Double totalAmount;
}
