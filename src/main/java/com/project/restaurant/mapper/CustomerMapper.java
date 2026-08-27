package com.project.restaurant.mapper;

import com.project.restaurant.dto.request.CreateCustomerRequest;
import com.project.restaurant.dto.request.UpdateCustomerRequest;
import com.project.restaurant.dto.response.CustomerResponse;
import com.project.restaurant.dto.response.SimpleCustomerResponse;
import com.project.restaurant.entity.Customer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "id", ignore = true)
    Customer toEntity(CreateCustomerRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(UpdateCustomerRequest request, @MappingTarget Customer customer);

    CustomerResponse toResponse(Customer customer);
    
    SimpleCustomerResponse toSimpleResponse(Customer customer);
}
