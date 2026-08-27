package com.project.restaurant.dto.response;
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
public class TransactionResponse {
    private String id;
    private SimpleCustomerResponse customer; // Nested DTO
    private LocalDateTime transactionDate;
    private List<TransactionDetailResponse> details; // List nested DTO
    private Double totalAmount;
    private String notes;
}