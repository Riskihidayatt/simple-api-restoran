package com.enigma.restaurant.service;

import com.enigma.restaurant.dto.request.CreateCustomerRequest;
import com.enigma.restaurant.dto.request.UpdateCustomerRequest;
import com.enigma.restaurant.dto.response.CustomerResponse;
import com.enigma.restaurant.dto.response.SimpleCustomerResponse;
import com.enigma.restaurant.entity.Customer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import java.util.List;

public interface CustomerService {

    CustomerResponse addCustomer(CreateCustomerRequest customerRequest);
    CustomerResponse updateCustomer(String id, @Valid UpdateCustomerRequest request);
    CustomerResponse deleteCustomer(String id);
    Page<SimpleCustomerResponse> getAllCustomers(String name, int page, int size, String sortBy, String direction);
    CustomerResponse getCustomerById(String id);
    Customer findByIdOrThrow(@NotBlank(message = "Customer ID tidak boleh kosong") String customerId);
}
