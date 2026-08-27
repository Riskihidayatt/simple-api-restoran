package com.project.restaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.restaurant.constant.ApiEndpoint;
import com.project.restaurant.dto.request.CreateCustomerRequest;
import com.project.restaurant.dto.request.UpdateCustomerRequest;
import com.project.restaurant.dto.response.CustomerResponse;
import com.project.restaurant.dto.response.SimpleCustomerResponse;
import com.project.restaurant.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    private CustomerResponse customerResponse;
    private SimpleCustomerResponse simpleCustomerResponse;

    @BeforeEach
    void setUp() {
        customerResponse = CustomerResponse.builder()
                .id("cust-1")
                .name("Budi Santoso")
                .email("budi@email.com")
                .phone("081234567890")
                .address("Jakarta")
                .createdAt(LocalDateTime.now())
                .build();

        simpleCustomerResponse = SimpleCustomerResponse.builder()
                .id("cust-1")
                .name("Budi Santoso")
                .email("budi@email.com")
                .phone("081234567890")
                .build();
    }

    @Test
    void addCustomer_Success() throws Exception {
        CreateCustomerRequest request = new CreateCustomerRequest("Budi Santoso", "budi@email.com", "081234567890", "Jakarta");

        when(customerService.addCustomer(any(CreateCustomerRequest.class))).thenReturn(customerResponse);

        mockMvc.perform(post(ApiEndpoint.CUSTOMER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.message").value("Customer berhasil ditambahkan"))
                .andExpect(jsonPath("$.data.name").value("Budi Santoso"));
    }

    @Test
    void updateCustomer_Success() throws Exception {
        UpdateCustomerRequest request = new UpdateCustomerRequest("Budi Update", "08999999999", "Bandung");

        when(customerService.updateCustomer(eq("cust-1"), any(UpdateCustomerRequest.class))).thenReturn(customerResponse);

        mockMvc.perform(put(ApiEndpoint.CUSTOMER + "/update/cust-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Customer berhasil diupdate"));
    }

    @Test
    void getAllCustomers_Success() throws Exception {
        Page<SimpleCustomerResponse> page = new PageImpl<>(List.of(simpleCustomerResponse), PageRequest.of(0, 10), 1);

        when(customerService.getAllCustomers(any(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get(ApiEndpoint.CUSTOMER)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Daftar customer berhasil diambil"))
                .andExpect(jsonPath("$.data[0].id").value("cust-1"))
                .andExpect(jsonPath("$.pagination.totalElements").value(1));
    }

    @Test
    void getCustomerById_Success() throws Exception {
        when(customerService.getCustomerById("cust-1")).thenReturn(customerResponse);

        mockMvc.perform(get(ApiEndpoint.CUSTOMER + "/search/cust-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.id").value("cust-1"));
    }

    @Test
    void deleteCustomer_Success() throws Exception {
        when(customerService.deleteCustomer("cust-1")).thenReturn(customerResponse);

        mockMvc.perform(delete(ApiEndpoint.CUSTOMER + "/delete/cust-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Customer berhasil dihapus"));
    }
}