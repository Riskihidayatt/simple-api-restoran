package com.enigma.restaurant.dto.response;

import com.enigma.restaurant.enums.MenuCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MenuResponse {
    private String id;
    private String name;
    private String description;
    private Double price;
    private MenuCategory category;
    private Boolean isAvailable;
}
