package com.enigma.restaurant.service;

import com.enigma.restaurant.dto.request.CreateTransactionRequest;
import com.enigma.restaurant.dto.response.SimpleTransactionResponse;
import com.enigma.restaurant.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface TransactionService {
    TransactionResponse createTransaction(CreateTransactionRequest request);
    TransactionResponse getTransactionById(String id);
    Page<SimpleTransactionResponse> getAllTransactions(String customerId, LocalDateTime startDate, LocalDateTime endDate, int page, int size, String sortBy, String direction);
}
