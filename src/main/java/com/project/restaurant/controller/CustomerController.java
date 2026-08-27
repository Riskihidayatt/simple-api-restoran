package com.project.restaurant.controller;

import com.project.restaurant.constant.ApiEndpoint;
import com.project.restaurant.dto.request.CreateCustomerRequest;
import com.project.restaurant.dto.request.UpdateCustomerRequest;
import com.project.restaurant.dto.response.CommonResponse;
import com.project.restaurant.dto.response.CustomerResponse;
import com.project.restaurant.dto.response.PaginationResponse;
import com.project.restaurant.dto.response.SimpleCustomerResponse;
import com.project.restaurant.service.CustomerService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.CUSTOMER)
@RequiredArgsConstructor
@Tag(name = "Customer", description = "API for customer data management")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Add new customer", description = "Add new customer data to the system")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Customer successfully added"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "409", description = "Duplicate customer data")
    })
    @PostMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> addCustomer(@Valid @RequestBody CreateCustomerRequest customerRequest) {
        return CommonResponse.created("Customer successfully added", customerService.addCustomer(customerRequest));
    }

    @Operation(summary = "Update customer", description = "Update customer data by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer successfully updated"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PutMapping(ApiEndpoint.CUSTOMER_UPDATE)
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(@PathVariable String id, @Valid @RequestBody UpdateCustomerRequest request) {
        return CommonResponse.ok("Customer successfully updated", customerService.updateCustomer(id, request));
    }

    @Operation(summary = "Get all customers", description = "Retrieve customer list with pagination and optional name filter")
    @ApiResponse(responseCode = "200", description = "Customer list successfully retrieved")
    @GetMapping
    public ResponseEntity<CommonResponse<List<SimpleCustomerResponse>>> getAllCustomers(
            @Parameter(description = "Filter by customer name") @RequestParam(required = false) String name,
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<SimpleCustomerResponse> customerPage = customerService.getAllCustomers(name, pageable);
        return CommonResponse.ok("Customer list successfully retrieved", customerPage.getContent(), new PaginationResponse(customerPage));
    }

    @Operation(summary = "Find customer by ID", description = "Retrieve customer details by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping(ApiEndpoint.CUSTOMER_SEARCH)
    public ResponseEntity<CommonResponse<CustomerResponse>> getCustomerById(@PathVariable String id) {
        return CommonResponse.ok("Customer found", customerService.getCustomerById(id));
    }

    @Operation(summary = "Delete customer", description = "Delete customer data by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @DeleteMapping(ApiEndpoint.CUSTOMER_DELETE)
    public ResponseEntity<CommonResponse<String>> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return CommonResponse.ok("Customer successfully deleted", null);
    }
}
