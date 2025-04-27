package com.enigma.restaurant.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SimpleCustomerResponse {
    private String id;
    private String name;
    private String email;
    private String phone;
}
