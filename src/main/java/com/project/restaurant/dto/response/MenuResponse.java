package com.project.restaurant.dto.response;

import com.project.restaurant.enums.MenuCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {
    private String id;
    private String name;
    private String description;
    private Double price;
    private MenuCategory category;
    private Boolean isAvailable;
}
