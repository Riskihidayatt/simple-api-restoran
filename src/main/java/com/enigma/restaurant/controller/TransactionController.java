package com.enigma.restaurant.controller;
import com.enigma.restaurant.constant.ApiEndpoint;
import com.enigma.restaurant.dto.response.CommonResponse;
import com.enigma.restaurant.dto.response.PaginationResponse;
import com.enigma.restaurant.dto.request.CreateTransactionRequest;
import com.enigma.restaurant.dto.response.CommonResponse;
import com.enigma.restaurant.dto.response.SimpleTransactionResponse;
import com.enigma.restaurant.dto.response.TransactionResponse;
import com.enigma.restaurant.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.TRANSACTION)
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    @PostMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> createTransaction(
            @Valid @RequestBody CreateTransactionRequest request
    ) {
        // Delegate creation logic to service
        TransactionResponse transactionResponse = transactionService.createTransaction(request);

        // Build standard response
        CommonResponse<TransactionResponse> response = new CommonResponse<>(
                "Transaksi berhasil dibuat",
                HttpStatus.CREATED.value(),
                transactionResponse
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(ApiEndpoint.TRANSACTION_SEARCH)
    public ResponseEntity<CommonResponse<TransactionResponse>> getTransactionById(
            @PathVariable String id
    ) {
        TransactionResponse transactionResponse = transactionService.getTransactionById(id);
        CommonResponse<TransactionResponse> response = new CommonResponse<>(
                "Transaksi ditemukan",
                HttpStatus.OK.value(),
                transactionResponse
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<SimpleTransactionResponse>>> getAllTransactions(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "transactionDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Page<SimpleTransactionResponse> transactionPage = transactionService.getAllTransactions(
                customerId, startDate, endDate, page, size, sortBy, direction
        );
        PaginationResponse pagination = PaginationResponse.builder()
                .currentPage(transactionPage.getNumber())
                .totalElements(transactionPage.getTotalElements())
                .totalPages(transactionPage.getTotalPages())
                .pageSize(transactionPage.getSize())
                .build();

        CommonResponse<List<SimpleTransactionResponse>> response = new CommonResponse<>(
                "Daftar transaksi berhasil diambil",
                HttpStatus.OK.value(),
                transactionPage.getContent(),
                pagination
        );
        return ResponseEntity.ok(response);
    }
}