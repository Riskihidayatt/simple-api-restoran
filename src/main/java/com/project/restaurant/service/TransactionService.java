package com.project.restaurant.service;

import com.project.restaurant.dto.request.CreateTransactionRequest;
import com.project.restaurant.dto.response.SimpleTransactionResponse;
import com.project.restaurant.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface TransactionService {
    TransactionResponse createTransaction(CreateTransactionRequest request);
    TransactionResponse getTransactionById(String id);
    Page<SimpleTransactionResponse> getAllTransactions(String customerId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
