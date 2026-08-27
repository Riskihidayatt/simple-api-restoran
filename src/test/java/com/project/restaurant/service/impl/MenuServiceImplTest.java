package com.project.restaurant.service.impl;

import com.project.restaurant.dto.request.CreateMenuRequest;
import com.project.restaurant.dto.request.UpdateMenuRequest;
import com.project.restaurant.dto.response.MenuResponse;
import com.project.restaurant.entity.Menu;
import com.project.restaurant.enums.MenuCategory;
import com.project.restaurant.exception.ResourceNotFoundException;
import com.project.restaurant.mapper.MenuMapper;
import com.project.restaurant.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
class MenuServiceImplTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuMapper menuMapper;

    @InjectMocks
    private MenuServiceImpl menuService;

    private Menu sampleMenu;
    private MenuResponse sampleMenuResponse;

    @BeforeEach
    void setUp() {
        sampleMenu = Menu.builder()
                .id("menu-1")
                .name("Nasi Goreng")
                .price(25000.0)
                .category(MenuCategory.FOOD)
                .isAvailable(true)
                .build();

        sampleMenuResponse = MenuResponse.builder()
                .id("menu-1")
                .name("Nasi Goreng")
                .price(25000.0)
                .category(MenuCategory.FOOD)
                .isAvailable(true)
                .build();
    }

    @Test
    void createMenu_Success() {
        CreateMenuRequest request = new CreateMenuRequest("Nasi Goreng", "Enak", 25000.0, MenuCategory.FOOD);
        
        when(menuMapper.toEntity(request)).thenReturn(sampleMenu);
        when(menuRepository.save(any(Menu.class))).thenReturn(sampleMenu);
        when(menuMapper.toResponse(sampleMenu)).thenReturn(sampleMenuResponse);

        MenuResponse response = menuService.createMenu(request);

        assertNotNull(response);
        assertEquals("Nasi Goreng", response.getName());
        verify(menuRepository, times(1)).save(any(Menu.class));
    }

    @Test
    void getMenuById_Success() {
        when(menuRepository.findById("menu-1")).thenReturn(Optional.of(sampleMenu));
        when(menuMapper.toResponse(sampleMenu)).thenReturn(sampleMenuResponse);

        MenuResponse response = menuService.getMenuById("menu-1");

        assertNotNull(response);
        assertEquals("menu-1", response.getId());
        verify(menuRepository, times(1)).findById("menu-1");
    }

    @Test
    void getMenuById_NotFound_ThrowsException() {
        when(menuRepository.findById("invalid-id")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            menuService.getMenuById("invalid-id");
        });
        verify(menuRepository, times(1)).findById("invalid-id");
    }

    @Test
    void getAllMenus_WithPagination_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Menu> menuPage = new PageImpl<>(List.of(sampleMenu));
        
        when(menuRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(menuPage);
        when(menuMapper.toResponse(sampleMenu)).thenReturn(sampleMenuResponse);

        Page<MenuResponse> result = menuService.getAllMenus(MenuCategory.FOOD, "Nasi", 30000.0, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Nasi Goreng", result.getContent().get(0).getName());
    }

    @Test
    void updateMenu_Success() {
        UpdateMenuRequest request = new UpdateMenuRequest("Nasi Goreng Spesial", "Pake Telur", 30000.0, true, MenuCategory.FOOD);
        
        when(menuRepository.findById("menu-1")).thenReturn(Optional.of(sampleMenu));
        when(menuRepository.save(any(Menu.class))).thenReturn(sampleMenu);
        when(menuMapper.toResponse(sampleMenu)).thenReturn(sampleMenuResponse);

        MenuResponse response = menuService.updateMenu("menu-1", request);

        assertNotNull(response);
        verify(menuMapper, times(1)).updateEntityFromRequest(request, sampleMenu);
        verify(menuRepository, times(1)).save(sampleMenu);
    }

    @Test
    void updateMenu_NotFound_ThrowsException() {
        UpdateMenuRequest request = new UpdateMenuRequest();
        when(menuRepository.findById("invalid-id")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            menuService.updateMenu("invalid-id", request);
        });
    }

    @Test
    void deleteMenu_Success() {
        when(menuRepository.findById("menu-1")).thenReturn(Optional.of(sampleMenu));
        when(menuMapper.toResponse(sampleMenu)).thenReturn(sampleMenuResponse);

        MenuResponse response = menuService.deleteMenu("menu-1");

        assertNotNull(response);
        verify(menuRepository, times(1)).delete(sampleMenu);
    }

    @Test
    void getAllMenus_NoPagination_Success() {
        when(menuRepository.findAll()).thenReturn(List.of(sampleMenu));
        when(menuMapper.toResponse(sampleMenu)).thenReturn(sampleMenuResponse);

        List<MenuResponse> result = menuService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}