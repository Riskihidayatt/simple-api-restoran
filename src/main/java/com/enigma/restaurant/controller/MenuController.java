package com.enigma.restaurant.controller;


import com.enigma.restaurant.constant.ApiEndpoint;
import com.enigma.restaurant.dto.request.CreateMenuRequest;
import com.enigma.restaurant.dto.response.CommonResponse;
import com.enigma.restaurant.dto.response.MenuResponse;
import com.enigma.restaurant.dto.response.PaginationResponse;
import com.enigma.restaurant.entity.Menu;
import com.enigma.restaurant.enums.MenuCategory;
import com.enigma.restaurant.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.MENU)
public class MenuController {
    public final MenuService menuService;
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<MenuResponse>> createMenu(
            @Valid @RequestBody CreateMenuRequest request
    ) {
        MenuResponse response = menuService.createMenu(request);
        CommonResponse<MenuResponse> commonResponse = new CommonResponse<>(
                "Menu berhasil dibuat",
                HttpStatus.CREATED.value(),
                response
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping(ApiEndpoint.MENU_ID)
    public ResponseEntity<CommonResponse<MenuResponse>> getMenuById(@PathVariable String id) {
        MenuResponse response = menuService.getMenuById(id);
        CommonResponse<MenuResponse> commonResponse = new CommonResponse<>(
                "Menu ditemukan",
                HttpStatus.OK.value(),
                response
        );
        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<MenuResponse>>> getAllMenus(
            @RequestParam(required = false) MenuCategory category,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Page<MenuResponse> menuPage = menuService.getAllMenus(category, name, maxPrice, page, size, sortBy, direction);
        PaginationResponse pagination = PaginationResponse.builder()
                .currentPage(menuPage.getNumber())
                .totalElements(menuPage.getTotalElements())
                .totalPages(menuPage.getTotalPages())
                .pageSize(menuPage.getSize())
                .build();

        CommonResponse<List<MenuResponse>> response = new CommonResponse<>(
                "Daftar menu berhasil diambil",
                HttpStatus.OK.value(),
                menuPage.getContent(),
                pagination
        );
        return ResponseEntity.ok(response);
    }


    @PutMapping(ApiEndpoint.MENU_UPDATE)
    public ResponseEntity<CommonResponse<MenuResponse>> updateMenu(@PathVariable String id, @Valid @RequestBody CreateMenuRequest request) {
        MenuResponse response = menuService.updateMenu(id,request);
        CommonResponse<MenuResponse> commonResponse = new CommonResponse<>(
                "Menu berhasil diupdate",
                HttpStatus.OK.value(),
                response
        );
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping(ApiEndpoint.MENU_DELETE)
    public ResponseEntity<CommonResponse<String>> deleteMenu(@PathVariable String id) {
        menuService.deleteMenu(id);
        CommonResponse<String> commonResponse = new CommonResponse<>(
                "Daftar menu berhasil dihapus",
                HttpStatus.OK.value(),
                null
        );
        return ResponseEntity.ok(commonResponse);
    }
}
