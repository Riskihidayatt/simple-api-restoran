package com.project.restaurant.controller;

import com.project.restaurant.constant.ApiEndpoint;
import com.project.restaurant.dto.request.CreateTransactionRequest;
import com.project.restaurant.dto.response.CommonResponse;
import com.project.restaurant.dto.response.PaginationResponse;
import com.project.restaurant.dto.response.SimpleTransactionResponse;
import com.project.restaurant.dto.response.TransactionResponse;
import com.project.restaurant.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
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
        return CommonResponse.created("Transaksi berhasil dibuat", transactionService.createTransaction(request));
    }

    @GetMapping(ApiEndpoint.TRANSACTION_SEARCH)
    public ResponseEntity<CommonResponse<TransactionResponse>> getTransactionById(
            @PathVariable String id
    ) {
        return CommonResponse.ok("Transaksi ditemukan", transactionService.getTransactionById(id));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<SimpleTransactionResponse>>> getAllTransactions(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @PageableDefault(page = 0, size = 10, sort = "transactionDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<SimpleTransactionResponse> transactionPage = transactionService.getAllTransactions(
                customerId, startDate, endDate, pageable
        );
        return CommonResponse.ok("Daftar transaksi berhasil diambil", transactionPage.getContent(), new PaginationResponse(transactionPage));
    }
}
