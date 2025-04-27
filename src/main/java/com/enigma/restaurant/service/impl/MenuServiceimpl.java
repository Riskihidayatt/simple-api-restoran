package com.enigma.restaurant.service.impl;
import com.enigma.restaurant.dto.request.CreateMenuRequest;
import com.enigma.restaurant.dto.response.CustomerResponse;
import com.enigma.restaurant.dto.response.MenuResponse;
import com.enigma.restaurant.entity.Customer;
import com.enigma.restaurant.entity.Menu;
import com.enigma.restaurant.enums.MenuCategory;
import com.enigma.restaurant.exception.ResourceNotFoundException;
import com.enigma.restaurant.repository.MenuRepository;
import com.enigma.restaurant.service.MenuService;
import com.enigma.restaurant.specification.MenuSpecification;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceimpl implements MenuService {

    private final MenuRepository menuRepository;
    public MenuServiceimpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<MenuResponse> getAll() {
        List<Menu> menus = menuRepository.findAll();
        return menuRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public MenuResponse createMenu(CreateMenuRequest request) {
        Menu menu = Menu.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .build();
        menuRepository.save(menu);
        return mapToResponse(menu);
    }

    @Override
    public MenuResponse getMenuById(String id) { // Menerima ID (bisa dianggap 'request' sederhana)

            Menu menu = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Menu tidak ditemukan"));
            return mapToResponse(menu);
    }

//    @Override
//    public MenuResponse getMenuById(String id) {
//        Menu menu = findByIdOrThrow(id);
//        return mapToResponse(menu);
//    }



    @Override
    public Page<MenuResponse> getAllMenus(MenuCategory category, String name, Double maxPrice, int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Menu> spec = MenuSpecification.getSpecification(category, name, maxPrice);
        Page<Menu> menuPage = menuRepository.findAll(spec, pageable);
        return menuPage.map(this::mapToResponse);
    }


    @Override
    public MenuResponse updateMenu(String id, @Valid CreateMenuRequest request) {
        Menu menu = findByIdOrThrow(id);
        menu.setName(request.getName());
        menu.setDescription(request.getDescription());
        menu.setPrice(request.getPrice());
//        menu.setIsAvailable(request.getIsAvailable());
        menu.setCategory(request.getCategory());
        return mapToResponse(menuRepository.save(menu));
    }

    public MenuResponse deleteMenu(String id) {
        Menu menu = findByIdOrThrow(id);
        menuRepository.delete(menu);
        return mapToResponse(menu);
    }


    // --- Helper Methods ---
    private Menu findByIdOrThrow(String id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu dengan ID " + id + " tidak ditemukan"));
    }


    private MenuResponse mapToResponse(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .category(menu.getCategory())
                .isAvailable(menu.getIsAvailable())
                .build();
    }

}


