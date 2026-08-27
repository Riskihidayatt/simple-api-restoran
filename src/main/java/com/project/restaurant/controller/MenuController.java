package com.project.restaurant.controller;

import com.project.restaurant.constant.ApiEndpoint;
import com.project.restaurant.dto.request.CreateMenuRequest;
import com.project.restaurant.dto.request.UpdateMenuRequest;
import com.project.restaurant.dto.response.CommonResponse;
import com.project.restaurant.dto.response.MenuResponse;
import com.project.restaurant.dto.response.PaginationResponse;
import com.project.restaurant.enums.MenuCategory;
import com.project.restaurant.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.MENU)
@RequiredArgsConstructor
@Tag(name = "Menu", description = "API for restaurant menu management")
public class MenuController {
    private final MenuService menuService;

    @Operation(summary = "Add new menu", description = "Add a new menu item to the restaurant menu list")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Menu successfully created"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    @PostMapping
    public ResponseEntity<CommonResponse<MenuResponse>> createMenu(
            @Valid @RequestBody CreateMenuRequest request
    ) {
        return CommonResponse.created("Menu successfully created", menuService.createMenu(request));
    }

    @Operation(summary = "Find menu by ID", description = "Retrieve menu details by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Menu found"),
            @ApiResponse(responseCode = "404", description = "Menu not found")
    })
    @GetMapping(ApiEndpoint.MENU_ID)
    public ResponseEntity<CommonResponse<MenuResponse>> getMenuById(@PathVariable String id) {
        return CommonResponse.ok("Menu found", menuService.getMenuById(id));
    }

    @Operation(summary = "Get all menus", description = "Retrieve menu list with pagination and optional filters (category, name, max price)")
    @ApiResponse(responseCode = "200", description = "Menu list successfully retrieved")
    @GetMapping
    public ResponseEntity<CommonResponse<List<MenuResponse>>> getAllMenus(
            @Parameter(description = "Filter by category: FOOD, BEVERAGE, DESSERT") @RequestParam(required = false) MenuCategory category,
            @Parameter(description = "Filter by menu name") @RequestParam(required = false) String name,
            @Parameter(description = "Filter by maximum price") @RequestParam(required = false) Double maxPrice,
            @ParameterObject @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<MenuResponse> menuPage = menuService.getAllMenus(category, name, maxPrice, pageable);
        return CommonResponse.ok("Menu list successfully retrieved", menuPage.getContent(), new PaginationResponse(menuPage));
    }

    @Operation(summary = "Update menu", description = "Update a menu item by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Menu successfully updated"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Menu not found")
    })
    @PutMapping(ApiEndpoint.MENU_UPDATE)
    public ResponseEntity<CommonResponse<MenuResponse>> updateMenu(@PathVariable String id, @Valid @RequestBody UpdateMenuRequest request) {
        return CommonResponse.ok("Menu successfully updated", menuService.updateMenu(id, request));
    }

    @Operation(summary = "Delete menu", description = "Delete a menu item by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Menu successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Menu not found")
    })

    @DeleteMapping(ApiEndpoint.MENU_DELETE)
    public ResponseEntity<CommonResponse<String>> deleteMenu(@PathVariable String id) {
        menuService.deleteMenu(id);
        return CommonResponse.ok("Menu successfully deleted", null);
    }
}
