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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer sampleCustomer;
    private CustomerResponse sampleCustomerResponse;
    private SimpleCustomerResponse sampleSimpleCustomerResponse;

    @BeforeEach
    void setUp() {
        sampleCustomer = Customer.builder()
                .id("cust-1")
                .name("Budi Santoso")
                .email("budi@email.com")
                .phone("08123456789")
                .build();

        sampleCustomerResponse = CustomerResponse.builder()
                .id("cust-1")
                .name("Budi Santoso")
                .email("budi@email.com")
                .phone("08123456789")
                .build();
                
        sampleSimpleCustomerResponse = SimpleCustomerResponse.builder()
                .id("cust-1")
                .name("Budi Santoso")
                .build();
    }

    @Test
    void addCustomer_Success() {
        CreateCustomerRequest request = new CreateCustomerRequest("Budi Santoso", "budi@email.com", "08123456789", "Jakarta");
        
        when(customerRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.findByPhone(request.getPhone())).thenReturn(Optional.empty());
        when(customerMapper.toEntity(request)).thenReturn(sampleCustomer);
        when(customerRepository.save(any(Customer.class))).thenReturn(sampleCustomer);
        when(customerMapper.toResponse(sampleCustomer)).thenReturn(sampleCustomerResponse);

        CustomerResponse response = customerService.addCustomer(request);

        assertNotNull(response);
        assertEquals("Budi Santoso", response.getName());
    }

    @Test
    void addCustomer_DuplicateEmail_ThrowsException() {
        CreateCustomerRequest request = new CreateCustomerRequest("Budi Santoso", "budi@email.com", "08123456789", "Jakarta");
        
        when(customerRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(sampleCustomer));

        assertThrows(DuplicateResourceException.class, () -> customerService.addCustomer(request));
        verify(customerRepository, never()).save(any());
    }

    @Test
    void addCustomer_DuplicatePhone_ThrowsException() {
        CreateCustomerRequest request = new CreateCustomerRequest("Budi Santoso", "budi@email.com", "08123456789", "Jakarta");
        
        when(customerRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.findByPhone(request.getPhone())).thenReturn(Optional.of(sampleCustomer));

        assertThrows(DuplicateResourceException.class, () -> customerService.addCustomer(request));
        verify(customerRepository, never()).save(any());
    }

    @Test
    void addCustomer_DataIntegrityViolation_ThrowsException() {
        CreateCustomerRequest request = new CreateCustomerRequest("Budi Santoso", "budi@email.com", "08123456789", "Jakarta");
        
        when(customerRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(customerRepository.findByPhone(request.getPhone())).thenReturn(Optional.empty());
        when(customerMapper.toEntity(request)).thenReturn(sampleCustomer);
        when(customerRepository.save(any(Customer.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DuplicateResourceException.class, () -> customerService.addCustomer(request));
    }

    @Test
    void getCustomerById_Success() {
        when(customerRepository.findById("cust-1")).thenReturn(Optional.of(sampleCustomer));
        when(customerMapper.toResponse(sampleCustomer)).thenReturn(sampleCustomerResponse);

        CustomerResponse response = customerService.getCustomerById("cust-1");

        assertNotNull(response);
        assertEquals("cust-1", response.getId());
    }

    @Test
    void getCustomerById_NotFound_ThrowsException() {
        when(customerRepository.findById("invalid")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById("invalid"));
    }

    @Test
    void updateCustomer_Success() {
        UpdateCustomerRequest request = new UpdateCustomerRequest("Budi Update", "0899999999", "Bandung");
        
        when(customerRepository.findById("cust-1")).thenReturn(Optional.of(sampleCustomer));
        when(customerRepository.findByPhone(request.getPhone())).thenReturn(Optional.empty());
        when(customerMapper.toResponse(any())).thenReturn(sampleCustomerResponse);

        CustomerResponse response = customerService.updateCustomer("cust-1", request);

        assertNotNull(response);
        verify(customerMapper, times(1)).updateEntityFromRequest(request, sampleCustomer);
        verify(customerRepository, times(1)).saveAndFlush(sampleCustomer);
    }
    
    @Test
    void updateCustomer_DuplicatePhone_ThrowsException() {
        UpdateCustomerRequest request = new UpdateCustomerRequest("Budi Update", "0899999999", "Bandung");
        
        when(customerRepository.findById("cust-1")).thenReturn(Optional.of(sampleCustomer));
        when(customerRepository.findByPhone(request.getPhone())).thenReturn(Optional.of(new Customer()));

        assertThrows(DuplicateResourceException.class, () -> customerService.updateCustomer("cust-1", request));
        verify(customerRepository, never()).saveAndFlush(any());
    }

    @Test
    void deleteCustomer_Success() {
        when(customerRepository.findById("cust-1")).thenReturn(Optional.of(sampleCustomer));
        when(customerMapper.toResponse(sampleCustomer)).thenReturn(sampleCustomerResponse);

        CustomerResponse response = customerService.deleteCustomer("cust-1");

        assertNotNull(response);
        verify(customerRepository, times(1)).delete(sampleCustomer);
    }

    @Test
    void getAllCustomers_Pagination_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> customerPage = new PageImpl<>(List.of(sampleCustomer));
        
        when(customerRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(customerPage);
        when(customerMapper.toSimpleResponse(sampleCustomer)).thenReturn(sampleSimpleCustomerResponse);

        Page<SimpleCustomerResponse> result = customerService.getAllCustomers("Budi", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getAllCustomer_NoPagination_Success() {
        when(customerRepository.findAll()).thenReturn(List.of(sampleCustomer));
        when(customerMapper.toResponse(sampleCustomer)).thenReturn(sampleCustomerResponse);

        List<CustomerResponse> result = customerService.getAllCustomer();

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}