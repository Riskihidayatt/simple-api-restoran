package com.project.restaurant.service;

import com.project.restaurant.dto.request.CreateCustomerRequest;
import com.project.restaurant.dto.request.UpdateCustomerRequest;
import com.project.restaurant.dto.response.CustomerResponse;
import com.project.restaurant.dto.response.SimpleCustomerResponse;
import com.project.restaurant.entity.Customer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    CustomerResponse addCustomer(CreateCustomerRequest customerRequest);
    CustomerResponse updateCustomer(String id, @Valid UpdateCustomerRequest request);
    CustomerResponse deleteCustomer(String id);
    Page<SimpleCustomerResponse> getAllCustomers(String name, Pageable pageable);
    CustomerResponse getCustomerById(String id);
    Customer findByIdOrThrow(@NotBlank(message = "Customer ID tidak boleh kosong") String customerId);
}
