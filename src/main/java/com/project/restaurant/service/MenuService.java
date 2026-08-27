package com.project.restaurant.service;

import com.project.restaurant.dto.request.CreateMenuRequest;
import com.project.restaurant.dto.request.UpdateMenuRequest;
import com.project.restaurant.dto.response.MenuResponse;
import com.project.restaurant.enums.MenuCategory;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MenuService {
    MenuResponse createMenu(CreateMenuRequest request);
    MenuResponse getMenuById(String id);
    List<MenuResponse> getAll();
    Page<MenuResponse> getAllMenus(MenuCategory category, String name, Double maxPrice, Pageable pageable);
    MenuResponse updateMenu(String id, @Valid UpdateMenuRequest request);
    MenuResponse deleteMenu(String id);
}
