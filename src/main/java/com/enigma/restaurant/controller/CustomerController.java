package com.enigma.restaurant.controller;

import com.enigma.restaurant.constant.ApiEndpoint;
import com.enigma.restaurant.dto.request.CreateCustomerRequest;
import com.enigma.restaurant.dto.request.CreateMenuRequest;
import com.enigma.restaurant.dto.request.UpdateCustomerRequest;
import com.enigma.restaurant.dto.response.*;
import com.enigma.restaurant.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.CUSTOMER)
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> addCustomer(@Valid @RequestBody CreateCustomerRequest customerRequest) {
        CustomerResponse response = customerService.addCustomer(customerRequest);
        CommonResponse<CustomerResponse> commonResponse = new CommonResponse<>(
                "Customer berhasil ditambahkan",
                HttpStatus.CREATED.value(),
                response
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);

    }

    @PutMapping(ApiEndpoint.CUSTOMER_UPDATE)
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(@PathVariable String id, @Valid @RequestBody UpdateCustomerRequest request) {
        CustomerResponse response = customerService.updateCustomer(id, request);
        CommonResponse<CustomerResponse> commonResponse = new CommonResponse<>(
                "Menu berhasil diupdate",
                HttpStatus.OK.value(),
                response
        );
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<SimpleCustomerResponse>>> getAllCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Page<SimpleCustomerResponse> customerPage = customerService.getAllCustomers(name, page, size, sortBy, direction);
        PaginationResponse pagination = PaginationResponse.builder()
                .currentPage(customerPage.getNumber())
                .totalElements(customerPage.getTotalElements())
                .totalPages(customerPage.getTotalPages())
                .pageSize(customerPage.getSize())
                .build();
        CommonResponse<List<SimpleCustomerResponse>> response = new CommonResponse<>(
                "Daftar customer berhasil diambil",
                HttpStatus.OK.value(),
                customerPage.getContent(),
                pagination
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping(ApiEndpoint.CUSTOMER_SEARCH)
    public ResponseEntity<CommonResponse<CustomerResponse>> getCustomerById(@PathVariable String id) {
        CustomerResponse response = customerService.getCustomerById(id);
        CommonResponse<CustomerResponse> commonResponse = new CommonResponse<>(
                "Customer ditemukan",
                HttpStatus.OK.value(),
                response
        );
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping(ApiEndpoint.CUSTOMER_DELETE)
    public ResponseEntity<CommonResponse<String>> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        CommonResponse<String> commonResponse = new CommonResponse<>(
                "Customer berhasil dihapus",
                HttpStatus.OK.value(),
                null // Data bisa null untuk delete
        );
        return ResponseEntity.ok(commonResponse);
    }




}
