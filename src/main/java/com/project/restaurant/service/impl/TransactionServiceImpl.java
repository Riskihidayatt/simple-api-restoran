package com.project.restaurant.service.impl;

import com.project.restaurant.dto.request.CreateTransactionRequest;
import com.project.restaurant.dto.request.TransactionItemRequest;
import com.project.restaurant.dto.response.SimpleTransactionResponse;
import com.project.restaurant.dto.response.TransactionResponse;
import com.project.restaurant.entity.Customer;
import com.project.restaurant.entity.Menu;
import com.project.restaurant.entity.Transaction;
import com.project.restaurant.entity.TransactionDetail;
import com.project.restaurant.exception.BadRequestException;
import com.project.restaurant.exception.ResourceNotFoundException;
import com.project.restaurant.mapper.TransactionMapper;
import com.project.restaurant.repository.MenuRepository;
import com.project.restaurant.repository.TransactionRepository;
import com.project.restaurant.service.CustomerService;
import com.project.restaurant.service.TransactionService;
import com.project.restaurant.specification.TransactionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final MenuRepository menuRepository;
    private final CustomerService customerService;
    private final TransactionMapper transactionMapper;

    @Transactional
    @Override
    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        Customer customer = customerService.findByIdOrThrow(request.getCustomerId());
        
        Transaction transaction = Transaction.builder()
                .customer(customer)
                .transactionDate(LocalDateTime.now())
                .notes(request.getNotes())
                .build();
                
        // N+1 Fix: Fetch all menus at once
        List<String> menuIds = request.getItems().stream()
                .map(TransactionItemRequest::getMenuId)
                .toList();
        Map<String, Menu> menuMap = menuRepository.findAllById(menuIds).stream()
                .collect(Collectors.toMap(Menu::getId, menu -> menu));

        double totalAmount = 0.0;
        List<TransactionDetail> details = new ArrayList<>();
        
        for (TransactionItemRequest item : request.getItems()) {
            Menu menu = menuMap.get(item.getMenuId());
            if (menu == null) {
                throw new ResourceNotFoundException("Menu with ID " + item.getMenuId() + " not found");
            }
            if (Boolean.FALSE.equals(menu.getIsAvailable())) {
                throw new BadRequestException("Menu '" + menu.getName()+ "' is currently unavailable.");
            }

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
        return transactionMapper.toResponse(savedTransaction);
    }

    @Override
    public TransactionResponse getTransactionById(String id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction with ID " + id + " not found"));
        return transactionMapper.toResponse(transaction);
    }
    
    @Override
    public Page<SimpleTransactionResponse> getAllTransactions(
            String customerId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Specification<Transaction> spec = TransactionSpecification.getSpecification(customerId, startDate, endDate);
        return transactionRepository.findAll(spec, pageable).map(transactionMapper::toSimpleResponse);
    }
}
