package com.enigma.restaurant.service;


import com.enigma.restaurant.dto.request.CreateMenuRequest;
import com.enigma.restaurant.dto.response.CustomerResponse;
import com.enigma.restaurant.dto.response.MenuResponse;
import com.enigma.restaurant.enums.MenuCategory;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MenuService {
    MenuResponse createMenu(CreateMenuRequest request);
    MenuResponse getMenuById(String id);
    List<MenuResponse> getAll();
    Page<MenuResponse> getAllMenus(MenuCategory category, String name, Double maxPrice, int page, int size, String sortBy, String direction);
    MenuResponse updateMenu(String id, @Valid CreateMenuRequest request);
    MenuResponse deleteMenu(String id);


}
