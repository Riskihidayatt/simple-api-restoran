package com.enigma.restaurant.service.impl;

import com.enigma.restaurant.dto.request.CreateTransactionRequest;
import com.enigma.restaurant.dto.request.TransactionItemRequest;
import com.enigma.restaurant.dto.response.*;
import com.enigma.restaurant.entity.Customer;
import com.enigma.restaurant.entity.Menu;
import com.enigma.restaurant.entity.Transaction;
import com.enigma.restaurant.entity.TransactionDetail;
import com.enigma.restaurant.exception.BadRequestException;
import com.enigma.restaurant.exception.ResourceNotFoundException;
import com.enigma.restaurant.repository.CustomerRepository;
import com.enigma.restaurant.repository.MenuRepository;
import com.enigma.restaurant.repository.TransactionRepository;
import com.enigma.restaurant.service.CustomerService;
import com.enigma.restaurant.service.TransactionService;
import com.enigma.restaurant.specification.TransactionSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionSeviceimpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final MenuRepository menuRepository;
    private final CustomerService customerService;

    public TransactionSeviceimpl(TransactionRepository transactionRepository, CustomerRepository customerRepository, MenuRepository menuRepository, CustomerService customerService) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        this.menuRepository = menuRepository;
        this.customerService = customerService;
    }

    @Override
    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        Customer customer = customerService.findByIdOrThrow(request.getCustomerId());
        Transaction transaction = Transaction.builder()
                .customer(customer)
                .transactionDate(LocalDateTime.now())
                .notes(request.getNotes())
                .build();
        double totalAmount = 0.0;
        List<TransactionDetail> details = new ArrayList<>();
        for (TransactionItemRequest item : request.getItems()) {
            Menu menu = menuRepository.findById(item.getMenuId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu dengan ID " + item.getMenuId() + " tidak ditemukan"));
            if (Boolean.FALSE.equals(menu.getIsAvailable())) {
                throw new BadRequestException("Menu '" + menu.getName()+ "' sedang tidak tersedia.");
            }

            // Buat TransactionDetail
            double subtotal = menu.getPrice() * item.getQuantity();
            TransactionDetail detail = TransactionDetail.builder()
                    .menu(menu)
                    .quantity(item.getQuantity())
                    .price(menu.getPrice())
                    .subtotal(subtotal)
                    .build();
            details.add(detail);
            totalAmount += subtotal;
        }
        transaction.setTotalAmount(totalAmount);
        details.forEach(transaction::addTransactionDetail);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return mapEntityToResponse(savedTransaction);

    }

    @Override
    public TransactionResponse getTransactionById(String id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaksi dengan ID " + id + " tidak ditemukan"));
        return mapEntityToResponse(transaction);
    }
    @Override
    public Page<SimpleTransactionResponse> getAllTransactions(
            String customerId, LocalDateTime startDate, LocalDateTime endDate,
            int page, int size, String sortBy, String direction)
    {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Transaction> spec = TransactionSpecification.getSpecification(customerId, startDate, endDate);
        Page<Transaction> transactionPage = transactionRepository.findAll(spec, pageable);
        return transactionPage.map(this::mapEntityToSimpleResponse);
    }
    private TransactionResponse mapEntityToResponse(Transaction transaction) {
        // Map Customer -> SimpleCustomerResponse
        SimpleCustomerResponse customerDto = SimpleCustomerResponse.builder()
                .id(transaction.getCustomer().getId())
                .name(transaction.getCustomer().getName())
                .phone(transaction.getCustomer().getPhone())
                .build();
        // Map Set<TransactionDetail> -> List<TransactionDetailResponse>
        List<TransactionDetailResponse> detailDtos = transaction.getTransactionDetails().stream()
                .map(detail -> TransactionDetailResponse.builder()
                        .menu(SimpleMenuResponse.builder() // Map Menu -> SimpleMenuResponse
                                .id(detail.getMenu().getId())
                                .name(detail.getMenu().getName())
                                .build())
                        .quantity(detail.getQuantity())
                        .price(detail.getPrice())
                        .subtotal(detail.getSubtotal())
                        .build())
                .collect(Collectors.toList());
        return TransactionResponse.builder()
                .id(transaction.getId())
                .customer(customerDto)
                .transactionDate(transaction.getTransactionDate())
                .details(detailDtos)
                .totalAmount(transaction.getTotalAmount())
                .notes(transaction.getNotes())
                .build();
    }

    private SimpleTransactionResponse mapEntityToSimpleResponse(Transaction transaction) {
        return SimpleTransactionResponse.builder()
                .id(transaction.getId())
                .customerName(transaction.getCustomer().getName()) // Get name from joined customer
                .transactionDate(transaction.getTransactionDate())
                .totalAmount(transaction.getTotalAmount())
                .build();
    }
}
