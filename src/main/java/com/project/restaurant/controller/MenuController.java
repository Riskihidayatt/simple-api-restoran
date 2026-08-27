package com.project.restaurant.controller;

import com.project.restaurant.constant.ApiEndpoint;
import com.project.restaurant.dto.request.CreateMenuRequest;
import com.project.restaurant.dto.request.UpdateMenuRequest;
import com.project.restaurant.dto.response.CommonResponse;
import com.project.restaurant.dto.response.MenuResponse;
import com.project.restaurant.dto.response.PaginationResponse;
import com.project.restaurant.enums.MenuCategory;
import com.project.restaurant.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.MENU)
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<CommonResponse<MenuResponse>> createMenu(
            @Valid @RequestBody CreateMenuRequest request
    ) {
        return CommonResponse.created("Menu berhasil dibuat", menuService.createMenu(request));
    }

    @GetMapping(ApiEndpoint.MENU_ID)
    public ResponseEntity<CommonResponse<MenuResponse>> getMenuById(@PathVariable String id) {
        return CommonResponse.ok("Menu ditemukan", menuService.getMenuById(id));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<MenuResponse>>> getAllMenus(
            @RequestParam(required = false) MenuCategory category,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double maxPrice,
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<MenuResponse> menuPage = menuService.getAllMenus(category, name, maxPrice, pageable);
        return CommonResponse.ok("Daftar menu berhasil diambil", menuPage.getContent(), new PaginationResponse(menuPage));
    }

    @PutMapping(ApiEndpoint.MENU_UPDATE)
    public ResponseEntity<CommonResponse<MenuResponse>> updateMenu(@PathVariable String id, @Valid @RequestBody UpdateMenuRequest request) {
        return CommonResponse.ok("Menu berhasil diupdate", menuService.updateMenu(id, request));
    }

    @DeleteMapping(ApiEndpoint.MENU_DELETE)
    public ResponseEntity<CommonResponse<String>> deleteMenu(@PathVariable String id) {
        menuService.deleteMenu(id);
        return CommonResponse.ok("Daftar menu berhasil dihapus", null);
    }
}
