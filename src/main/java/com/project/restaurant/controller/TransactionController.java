package com.project.restaurant.controller;

import com.project.restaurant.constant.ApiEndpoint;
import com.project.restaurant.dto.request.CreateTransactionRequest;
import com.project.restaurant.dto.response.CommonResponse;
import com.project.restaurant.dto.response.PaginationResponse;
import com.project.restaurant.dto.response.SimpleTransactionResponse;
import com.project.restaurant.dto.response.TransactionResponse;
import com.project.restaurant.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Transaction", description = "API for purchase transaction management")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Create new transaction", description = "Create a new purchase transaction with a list of menu items")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transaction successfully created"),
            @ApiResponse(responseCode = "400", description = "Validation failed or menu unavailable"),
            @ApiResponse(responseCode = "404", description = "Customer or menu not found")
    })
    @PostMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> createTransaction(
            @Valid @RequestBody CreateTransactionRequest request
    ) {
        return CommonResponse.created("Transaction successfully created", transactionService.createTransaction(request));
    }

    @Operation(summary = "Find transaction by ID", description = "Retrieve full transaction details by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transaction found"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @GetMapping(ApiEndpoint.TRANSACTION_SEARCH)
    public ResponseEntity<CommonResponse<TransactionResponse>> getTransactionById(
            @PathVariable String id
    ) {
        return CommonResponse.ok("Transaction found", transactionService.getTransactionById(id));
    }

    @Operation(summary = "Get all transactions", description = "Retrieve transaction list with pagination and optional filters (customer, date range)")
    @ApiResponse(responseCode = "200", description = "Transaction list successfully retrieved")
    @GetMapping
    public ResponseEntity<CommonResponse<List<SimpleTransactionResponse>>> getAllTransactions(
            @Parameter(description = "Filter by customer ID") @RequestParam(required = false) String customerId,
            @Parameter(description = "Filter start date (ISO format: yyyy-MM-ddTHH:mm:ss)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Filter end date (ISO format: yyyy-MM-ddTHH:mm:ss)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @PageableDefault(page = 0, size = 10, sort = "transactionDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<SimpleTransactionResponse> transactionPage = transactionService.getAllTransactions(
                customerId, startDate, endDate, pageable
        );
        return CommonResponse.ok("Transaction list successfully retrieved", transactionPage.getContent(), new PaginationResponse(transactionPage));
    }
}
