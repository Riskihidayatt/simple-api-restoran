package com.enigma.restaurant.dto.response;
import com.enigma.restaurant.dto.response.SimpleCustomerResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class TransactionResponse {
    private String id;
    private SimpleCustomerResponse customer; // Nested DTO
    private LocalDateTime transactionDate;
    private List<TransactionDetailResponse> details; // List nested DTO
    private Double totalAmount;
    private String notes;
}