package com.project.restaurant.service.impl;

import com.project.restaurant.dto.request.CreateTransactionRequest;
import com.project.restaurant.dto.request.TransactionItemRequest;
import com.project.restaurant.dto.response.SimpleTransactionResponse;
import com.project.restaurant.dto.response.TransactionResponse;
import com.project.restaurant.entity.Customer;
import com.project.restaurant.entity.Menu;
import com.project.restaurant.entity.Transaction;
import com.project.restaurant.exception.BadRequestException;
import com.project.restaurant.exception.ResourceNotFoundException;
import com.project.restaurant.mapper.TransactionMapper;
import com.project.restaurant.repository.MenuRepository;
import com.project.restaurant.repository.TransactionRepository;
import com.project.restaurant.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Customer sampleCustomer;
    private Menu sampleMenu;
    private Transaction sampleTransaction;
    private TransactionResponse sampleResponse;
    private SimpleTransactionResponse sampleSimpleResponse;

    @BeforeEach
    void setUp() {
        sampleCustomer = Customer.builder().id("cust-1").name("Budi").build();
        
        sampleMenu = Menu.builder()
                .id("menu-1")
                .name("Nasi Goreng")
                .price(25000.0)
                .isAvailable(true)
                .build();
                
        sampleTransaction = Transaction.builder()
                .id("trx-1")
                .customer(sampleCustomer)
                .totalAmount(50000.0)
                .build();
                
        sampleResponse = TransactionResponse.builder()
                .id("trx-1")
                .totalAmount(50000.0)
                .build();
                
        sampleSimpleResponse = SimpleTransactionResponse.builder()
                .id("trx-1")
                .totalAmount(50000.0)
                .build();
    }

    @Test
    void createTransaction_Success() {
        TransactionItemRequest itemRequest = new TransactionItemRequest("menu-1", 2);
        CreateTransactionRequest request = new CreateTransactionRequest("cust-1", List.of(itemRequest), "Catatan");

        when(customerService.findByIdOrThrow("cust-1")).thenReturn(sampleCustomer);
        when(menuRepository.findAllById(List.of("menu-1"))).thenReturn(List.of(sampleMenu));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(sampleTransaction);
        when(transactionMapper.toResponse(sampleTransaction)).thenReturn(sampleResponse);

        TransactionResponse response = transactionService.createTransaction(request);

        assertNotNull(response);
        assertEquals(50000.0, response.getTotalAmount());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void createTransaction_MenuNotFound_ThrowsException() {
        TransactionItemRequest itemRequest = new TransactionItemRequest("invalid-menu", 2);
        CreateTransactionRequest request = new CreateTransactionRequest("cust-1", List.of(itemRequest), "Catatan");

        when(customerService.findByIdOrThrow("cust-1")).thenReturn(sampleCustomer);
        when(menuRepository.findAllById(anyList())).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> transactionService.createTransaction(request));
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void createTransaction_MenuNotAvailable_ThrowsException() {
        sampleMenu.setIsAvailable(false);
        TransactionItemRequest itemRequest = new TransactionItemRequest("menu-1", 2);
        CreateTransactionRequest request = new CreateTransactionRequest("cust-1", List.of(itemRequest), "Catatan");

        when(customerService.findByIdOrThrow("cust-1")).thenReturn(sampleCustomer);
        when(menuRepository.findAllById(List.of("menu-1"))).thenReturn(List.of(sampleMenu));

        assertThrows(BadRequestException.class, () -> transactionService.createTransaction(request));
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void getTransactionById_Success() {
        when(transactionRepository.findById("trx-1")).thenReturn(Optional.of(sampleTransaction));
        when(transactionMapper.toResponse(sampleTransaction)).thenReturn(sampleResponse);

        TransactionResponse response = transactionService.getTransactionById("trx-1");

        assertNotNull(response);
        assertEquals("trx-1", response.getId());
    }

    @Test
    void getTransactionById_NotFound_ThrowsException() {
        when(transactionRepository.findById("invalid")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> transactionService.getTransactionById("invalid"));
    }

    @Test
    void getAllTransactions_Pagination_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Transaction> transactionPage = new PageImpl<>(List.of(sampleTransaction));
        
        when(transactionRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(transactionPage);
        when(transactionMapper.toSimpleResponse(sampleTransaction)).thenReturn(sampleSimpleResponse);

        Page<SimpleTransactionResponse> result = transactionService.getAllTransactions("cust-1", LocalDateTime.now(), LocalDateTime.now(), pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }
}
