package com.enigma.restaurant.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SimpleMenuResponse {
    private String id;
    private String name;
    private Double price;
    private String category;
    private Boolean isAvailable;
}
