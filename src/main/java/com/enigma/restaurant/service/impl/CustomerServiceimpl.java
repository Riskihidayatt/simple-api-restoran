package com.enigma.restaurant.service.impl;

import com.enigma.restaurant.dto.request.CreateCustomerRequest;
import com.enigma.restaurant.dto.request.UpdateCustomerRequest;
import com.enigma.restaurant.dto.response.CustomerResponse;
import com.enigma.restaurant.dto.response.SimpleCustomerResponse;
import com.enigma.restaurant.entity.Customer;
import com.enigma.restaurant.exception.DuplicateResourceException;
import com.enigma.restaurant.exception.ResourceNotFoundException;
import com.enigma.restaurant.repository.CustomerRepository;
import com.enigma.restaurant.service.CustomerService;
import com.enigma.restaurant.specification.CustomerSpecification;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerServiceimpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceimpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse mapToResponse(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone(), customer.getAddress(),customer.getCreatedAt(),customer.getUpdatedAt());
    }

    @Override
    public CustomerResponse addCustomer(CreateCustomerRequest customerRequest) {
        if (customerRequest.getEmail() != null && customerRepository.findByEmail(customerRequest.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email " +customerRequest.getEmail() + " sudah terdaftar");
        }
        if (customerRepository.findByPhone(customerRequest.getPhone()).isPresent()) {
            throw new DuplicateResourceException("Nomor telepon " + customerRequest.getPhone() + " sudah terdaftar");
        }
        Customer customer = Customer.builder()
                .name(customerRequest.getName())
                .email(customerRequest.getEmail())
                .phone(customerRequest.getPhone())
                .address(customerRequest.getAddress())
                .build();
            try {
                customerRepository.save(customer);
            } catch (DataIntegrityViolationException e) {
                throw new DuplicateResourceException("Email atau nomor telepon sudah terdaftar" + e);
            }
            return mapToResponse(customer);
    }


    public List<CustomerResponse> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::mapToResponse).toList();
    }
    
    public CustomerResponse deleteCustomer(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer tidak ditemukan"));
        customerRepository.delete(customer);
        return mapToResponse(customer);
    }


    @Override
    public CustomerResponse updateCustomer(String id, UpdateCustomerRequest request) {
        Customer customer = findByIdOrThrow(id);
        if (request.getPhone() != null && !request.getPhone().equals(customer.getPhone())) {
            if (customerRepository.findByPhone(request.getPhone()).isPresent()) {
                throw new DuplicateResourceException("Nomor telepon " + request.getPhone() + " sudah terdaftar");
            }
            customer.setPhone(request.getPhone());
        }if (request.getName() != null) customer.setName(request.getName());
        if (request.getAddress() != null) customer.setAddress(request.getAddress());
        try {
            customerRepository.saveAndFlush(customer);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Gagal update: Email atau nomor telepon mungkin sudah ada.", e);
        }
        return mapToResponse(customer);
    }

    @Override
    public Page<SimpleCustomerResponse> getAllCustomers(String name, int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Customer> spec = CustomerSpecification.getSpecification(name);
        Page<Customer> customerPage = customerRepository.findAll(spec, pageable);
        return customerPage.map(this::mapToSimpleResponse);
    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer dengan ID " + id + " tidak ditemukan"));
        return mapToResponse(customer);
    }

    // --- Helper Methods ---
    public Customer findByIdOrThrow(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu dengan ID " + id + " tidak ditemukan"));
    }


    private SimpleCustomerResponse mapToSimpleResponse(Customer customer) {
        return SimpleCustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone()) // Sesuai contoh API
                .build();
    }






}
