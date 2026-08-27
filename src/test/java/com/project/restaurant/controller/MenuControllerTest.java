package com.project.restaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.restaurant.constant.ApiEndpoint;
import com.project.restaurant.dto.request.CreateMenuRequest;
import com.project.restaurant.dto.request.UpdateMenuRequest;
import com.project.restaurant.dto.response.MenuResponse;
import com.project.restaurant.enums.MenuCategory;
import com.project.restaurant.service.MenuService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MenuController.class)
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MenuService menuService;

    private MenuResponse menuResponse;

    @BeforeEach
    void setUp() {
        menuResponse = MenuResponse.builder()
                .id("menu-1")
                .name("Nasi Goreng")
                .price(25000.0)
                .category(MenuCategory.FOOD)
                .isAvailable(true)
                .build();
    }

    @Test
    void createMenu_Success() throws Exception {
        CreateMenuRequest request = new CreateMenuRequest("Nasi Goreng", "Enak", 25000.0, MenuCategory.FOOD);

        when(menuService.createMenu(any(CreateMenuRequest.class))).thenReturn(menuResponse);

        mockMvc.perform(post(ApiEndpoint.MENU)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.message").value("Menu berhasil dibuat"))
                .andExpect(jsonPath("$.data.name").value("Nasi Goreng"));
    }

    @Test
    void getMenuById_Success() throws Exception {
        when(menuService.getMenuById("menu-1")).thenReturn(menuResponse);

        mockMvc.perform(get(ApiEndpoint.MENU + "/search/menu-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.id").value("menu-1"));
    }

    @Test
    void getAllMenus_Success() throws Exception {
        Page<MenuResponse> page = new PageImpl<>(List.of(menuResponse), PageRequest.of(0, 10), 1);

        when(menuService.getAllMenus(any(), any(), any(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get(ApiEndpoint.MENU)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Daftar menu berhasil diambil"))
                .andExpect(jsonPath("$.data[0].id").value("menu-1"))
                .andExpect(jsonPath("$.pagination.totalElements").value(1));
    }

    @Test
    void updateMenu_Success() throws Exception {
        UpdateMenuRequest request = new UpdateMenuRequest("Nasi Goreng Spesial", "Pake Telur", 30000.0, true, MenuCategory.FOOD);

        when(menuService.updateMenu(eq("menu-1"), any(UpdateMenuRequest.class))).thenReturn(menuResponse);

        mockMvc.perform(put(ApiEndpoint.MENU + "/update/menu-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Menu berhasil diupdate"));
    }

    @Test
    void deleteMenu_Success() throws Exception {
        when(menuService.deleteMenu("menu-1")).thenReturn(menuResponse);

        mockMvc.perform(delete(ApiEndpoint.MENU + "/delete/menu-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Daftar menu berhasil dihapus"));
    }
}