package com.project.restaurant.mapper;

import com.project.restaurant.dto.response.SimpleCustomerResponse;
import com.project.restaurant.dto.response.SimpleMenuResponse;
import com.project.restaurant.dto.response.SimpleTransactionResponse;
import com.project.restaurant.dto.response.TransactionDetailResponse;
import com.project.restaurant.dto.response.TransactionResponse;
import com.project.restaurant.entity.Customer;
import com.project.restaurant.entity.Menu;
import com.project.restaurant.entity.Transaction;
import com.project.restaurant.entity.TransactionDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "details", source = "transactionDetails")
    TransactionResponse toResponse(Transaction transaction);

    @Mapping(target = "customerName", source = "customer.name")
    SimpleTransactionResponse toSimpleResponse(Transaction transaction);

    SimpleCustomerResponse toSimpleCustomerResponse(Customer customer);
    
    @Mapping(target = "menu", source = "menu")
    TransactionDetailResponse toTransactionDetailResponse(TransactionDetail detail);
    
    SimpleMenuResponse toSimpleMenuResponse(Menu menu);
}
