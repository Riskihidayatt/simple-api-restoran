package com.project.restaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.restaurant.constant.ApiEndpoint;
import com.project.restaurant.dto.request.CreateTransactionRequest;
import com.project.restaurant.dto.request.TransactionItemRequest;
import com.project.restaurant.dto.response.SimpleTransactionResponse;
import com.project.restaurant.dto.response.TransactionResponse;
import com.project.restaurant.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    private TransactionResponse transactionResponse;
    private SimpleTransactionResponse simpleTransactionResponse;

    @BeforeEach
    void setUp() {
        transactionResponse = TransactionResponse.builder()
                .id("trx-1")
                .totalAmount(50000.0)
                .build();

        simpleTransactionResponse = SimpleTransactionResponse.builder()
                .id("trx-1")
                .totalAmount(50000.0)
                .transactionDate(LocalDateTime.now())
                .build();
    }

    @Test
    void createTransaction_Success() throws Exception {
        TransactionItemRequest itemRequest = new TransactionItemRequest("menu-1", 2);
        CreateTransactionRequest request = new CreateTransactionRequest("cust-1", List.of(itemRequest), "Catatan");

        when(transactionService.createTransaction(any(CreateTransactionRequest.class))).thenReturn(transactionResponse);

        mockMvc.perform(post(ApiEndpoint.TRANSACTION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.message").value("Transaksi berhasil dibuat"))
                .andExpect(jsonPath("$.data.id").value("trx-1"));
    }

    @Test
    void getTransactionById_Success() throws Exception {
        when(transactionService.getTransactionById("trx-1")).thenReturn(transactionResponse);

        mockMvc.perform(get(ApiEndpoint.TRANSACTION + "/search/trx-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.id").value("trx-1"));
    }

    @Test
    void getAllTransactions_Success() throws Exception {
        Page<SimpleTransactionResponse> page = new PageImpl<>(List.of(simpleTransactionResponse), PageRequest.of(0, 10), 1);

        when(transactionService.getAllTransactions(any(), any(), any(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get(ApiEndpoint.TRANSACTION)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Daftar transaksi berhasil diambil"))
                .andExpect(jsonPath("$.data[0].id").value("trx-1"))
                .andExpect(jsonPath("$.pagination.totalElements").value(1));
    }
}