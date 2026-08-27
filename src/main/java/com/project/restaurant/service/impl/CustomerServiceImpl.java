package com.project.restaurant.service.impl;

import com.project.restaurant.dto.request.CreateCustomerRequest;
import com.project.restaurant.dto.request.UpdateCustomerRequest;
import com.project.restaurant.dto.response.CustomerResponse;
import com.project.restaurant.dto.response.SimpleCustomerResponse;
import com.project.restaurant.entity.Customer;
import com.project.restaurant.exception.DuplicateResourceException;
import com.project.restaurant.exception.ResourceNotFoundException;
import com.project.restaurant.mapper.CustomerMapper;
import com.project.restaurant.repository.CustomerRepository;
import com.project.restaurant.service.CustomerService;
import com.project.restaurant.specification.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse addCustomer(CreateCustomerRequest customerRequest) {
        if (customerRequest.getEmail() != null && customerRepository.findByEmail(customerRequest.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email " + customerRequest.getEmail() + " is already registered");
        }
        if (customerRepository.findByPhone(customerRequest.getPhone()).isPresent()) {
            throw new DuplicateResourceException("Phone number " + customerRequest.getPhone() + " is already registered");
        }
        Customer customer = customerMapper.toEntity(customerRequest);
        try {
            customerRepository.save(customer);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Email or phone number is already registered", e);
        }
        return customerMapper.toResponse(customer);
    }

    public List<CustomerResponse> getAllCustomer() {
        return customerRepository.findAll().stream().map(customerMapper::toResponse).toList();
    }

    @Override
    public CustomerResponse deleteCustomer(String id) {
        Customer customer = findByIdOrThrow(id);
        customerRepository.delete(customer);
        return customerMapper.toResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(String id, UpdateCustomerRequest request) {
        Customer customer = findByIdOrThrow(id);
        if (request.getPhone() != null && !request.getPhone().equals(customer.getPhone())) {
            if (customerRepository.findByPhone(request.getPhone()).isPresent()) {
                throw new DuplicateResourceException("Phone number " + request.getPhone() + " is already registered");
            }
        }
        customerMapper.updateEntityFromRequest(request, customer);
        try {
            customerRepository.saveAndFlush(customer);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Update failed: Email or phone number may already exist.", e);
        }
        return customerMapper.toResponse(customer);
    }

    @Override
    public Page<SimpleCustomerResponse> getAllCustomers(String name, Pageable pageable) {
        Specification<Customer> spec = CustomerSpecification.getSpecification(name);
        return customerRepository.findAll(spec, pageable).map(customerMapper::toSimpleResponse);
    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        return customerMapper.toResponse(findByIdOrThrow(id));
    }

    public Customer findByIdOrThrow(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with ID " + id + " not found"));
    }
}
