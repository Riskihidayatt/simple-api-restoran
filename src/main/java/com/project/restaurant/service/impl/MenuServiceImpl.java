package com.project.restaurant.service.impl;

import com.project.restaurant.dto.request.CreateMenuRequest;
import com.project.restaurant.dto.request.UpdateMenuRequest;
import com.project.restaurant.dto.response.MenuResponse;
import com.project.restaurant.entity.Menu;
import com.project.restaurant.enums.MenuCategory;
import com.project.restaurant.exception.ResourceNotFoundException;
import com.project.restaurant.mapper.MenuMapper;
import com.project.restaurant.repository.MenuRepository;
import com.project.restaurant.service.MenuService;
import com.project.restaurant.specification.MenuSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    @Override
    public List<MenuResponse> getAll() {
        return menuRepository.findAll().stream().map(menuMapper::toResponse).toList();
    }

    @Override
    public MenuResponse createMenu(CreateMenuRequest request) {
        Menu menu = menuMapper.toEntity(request);
        menuRepository.save(menu);
        return menuMapper.toResponse(menu);
    }

    @Override
    public MenuResponse getMenuById(String id) {
        return menuMapper.toResponse(findByIdOrThrow(id));
    }

    @Override
    public Page<MenuResponse> getAllMenus(MenuCategory category, String name, Double maxPrice, Pageable pageable) {
        Specification<Menu> spec = MenuSpecification.getSpecification(category, name, maxPrice);
        return menuRepository.findAll(spec, pageable).map(menuMapper::toResponse);
    }

    @Override
    public MenuResponse updateMenu(String id, @Valid UpdateMenuRequest request) {
        Menu menu = findByIdOrThrow(id);
        menuMapper.updateEntityFromRequest(request, menu);
        return menuMapper.toResponse(menuRepository.save(menu));
    }

    @Override
    public MenuResponse deleteMenu(String id) {
        Menu menu = findByIdOrThrow(id);
        menuRepository.delete(menu);
        return menuMapper.toResponse(menu);
    }

    private Menu findByIdOrThrow(String id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu with ID " + id + " not found"));
    }
}
