package com.project.restaurant.controller;

import com.project.restaurant.constant.ApiEndpoint;
import com.project.restaurant.dto.request.CreateCustomerRequest;
import com.project.restaurant.dto.request.UpdateCustomerRequest;
import com.project.restaurant.dto.response.CommonResponse;
import com.project.restaurant.dto.response.CustomerResponse;
import com.project.restaurant.dto.response.PaginationResponse;
import com.project.restaurant.dto.response.SimpleCustomerResponse;
import com.project.restaurant.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.CUSTOMER)
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> addCustomer(@Valid @RequestBody CreateCustomerRequest customerRequest) {
        return CommonResponse.created("Customer berhasil ditambahkan", customerService.addCustomer(customerRequest));
    }

    @PutMapping(ApiEndpoint.CUSTOMER_UPDATE)
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(@PathVariable String id, @Valid @RequestBody UpdateCustomerRequest request) {
        return CommonResponse.ok("Customer berhasil diupdate", customerService.updateCustomer(id, request));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<SimpleCustomerResponse>>> getAllCustomers(
            @RequestParam(required = false) String name,
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<SimpleCustomerResponse> customerPage = customerService.getAllCustomers(name, pageable);
        return CommonResponse.ok("Daftar customer berhasil diambil", customerPage.getContent(), new PaginationResponse(customerPage));
    }

    @GetMapping(ApiEndpoint.CUSTOMER_SEARCH)
    public ResponseEntity<CommonResponse<CustomerResponse>> getCustomerById(@PathVariable String id) {
        return CommonResponse.ok("Customer ditemukan", customerService.getCustomerById(id));
    }

    @DeleteMapping(ApiEndpoint.CUSTOMER_DELETE)
    public ResponseEntity<CommonResponse<String>> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return CommonResponse.ok("Customer berhasil dihapus", null);
    }
}
